// via AI.
// generate.gradle.kts
// from prev project
abstract class GenerateResourcesObjectsTask : DefaultTask() {
    @get:InputDirectory
    abstract val resourcesInputDir: DirectoryProperty

    @get:OutputDirectory
    abstract val generatedOutputDir: DirectoryProperty

    init {
        group = "generation"
        description = "Generates R*.kt objects from resources"
    }

    @TaskAction
    fun generate() {
        val inputDir = resourcesInputDir.get().asFile.apply {
            if (!exists()) throw GradleException("❌ Resources directory does not exist: ${absolutePath}")
        }

        val outputDir = generatedOutputDir.get().asFile.apply {
            deleteRecursively()
            mkdirs()
        }

        logger.lifecycle("🔍 Scanning resources in: ${inputDir.path}")

        // Определяем базовый тип ресурса (drawable, files и т.д.)
        val resourceType = inputDir.name

        inputDir.listFiles()?.forEach { categoryDir ->
            if (categoryDir.isDirectory) {
                generateResourcesObject(categoryDir, outputDir, resourceType)
            }
        }
    }

    private fun generateResourcesObject(resourcesDir: File, outputDir: File, resourceType: String) {
        val categoryName = resourcesDir.name
        val objectName = "R${categoryName.replaceFirstChar { it.uppercase() }}"
        val outputFile = File(outputDir, "${objectName}.kt")

        val resources = scanResources(resourcesDir, resourceType, categoryName = categoryName)
        outputFile.writeText(generateObjectContent(objectName, resources))
        logger.lifecycle("✅ Generated $objectName with ${resources.size} resources")
    }

    private fun scanResources(dir: File, resourceType: String, currentPath: String = "", categoryName: String): List<ResourceInfo> {
        val resources = mutableListOf<ResourceInfo>()

        dir.listFiles()?.forEach { file ->
            when {
                file.isDirectory -> {
                    val newPath = if (currentPath.isEmpty()) file.name else "$currentPath/${file.name}"
                    resources.addAll(scanResources(file, resourceType, newPath, categoryName=categoryName))
                }
                file.isFile && isSupportedFile(file) -> {
                    val constName = generateConstName(file.nameWithoutExtension)
                    // Формируем путь без дублирования resourceType в пути
                    val relativePath = if (currentPath.isEmpty()) {
                        "$resourceType/$categoryName/${file.name}"
                    } else {
                        "$resourceType/$categoryName/$currentPath/${file.name}"
                    }.replace("$resourceType/$resourceType/", "$resourceType/") // Фикс дублирования
                    resources.add(ResourceInfo(constName, relativePath, currentPath))
                }
            }
        }

        return resources
    }

    private fun generateConstName(filename: String): String {
        return filename
            .replace("-", "_")
            .replace(".", "_")
            .uppercase()
    }

    private fun isSupportedFile(file: File): Boolean {
        return file.extension.matches(Regex("svg|png|jpg|jpeg|webp|xml|json|txt|pdf"))
    }

    private fun generateObjectContent(objectName: String, resources: List<ResourceInfo>): String {
        // Создаем дерево ресурсов
        val root = ResourceNode("")

        resources.forEach { resource ->
            var currentNode = root
            val pathParts = resource.path.split('/').filter { it.isNotEmpty() }

            pathParts.forEach { part ->
                currentNode = currentNode.children.getOrPut(part) { ResourceNode(part) }
            }

            currentNode.resources.add(resource)
        }

        return buildString {
            appendLine("package resources")
            appendLine()
            appendLine("// Auto-generated file - DO NOT EDIT")
            appendLine("@Suppress(\"unused\")")
            appendLine("data object $objectName {")

            // Добавляем ресурсы верхнего уровня
            root.resources.forEach { res ->
                appendLine("    const val ${res.constName} = \"${res.relativePath}\"")
            }

            // Рекурсивно добавляем вложенные объекты
            appendNestedObjects(root, 1)

            appendLine("}")
        }
    }

    private fun StringBuilder.appendNestedObjects(node: ResourceNode, indentLevel: Int) {
        node.children.entries.sortedBy { it.key }.forEach { (name, childNode) ->
            val indent = "    ".repeat(indentLevel)
            appendLine("$indent data object ${name.uppercase()} {")

            // Добавляем ресурсы текущего уровня
            childNode.resources.forEach { res ->
                val resourceIndent = "    ".repeat(indentLevel + 1)
                appendLine("$resourceIndent const val ${res.constName} = \"${res.relativePath}\"")
            }

            // Рекурсивно добавляем вложенные объекты
            appendNestedObjects(childNode, indentLevel + 1)

            appendLine("$indent }")
        }
    }

    private class ResourceNode(val name: String) {
        val children = mutableMapOf<String, ResourceNode>()
        val resources = mutableListOf<ResourceInfo>()
    }

    private data class ResourceInfo(
        val constName: String,
        val relativePath: String,
        val path: String
    )
}

val generateResourcesObjects by tasks.registering(GenerateResourcesObjectsTask::class) {
    resourcesInputDir.set(project.layout.projectDirectory.dir("src/commonMain/composeResources/drawable"))
    generatedOutputDir.set(project.layout.buildDirectory.dir("generated/sources/resourceObjects"))
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(generateResourcesObjects)
}

tasks.named("clean") {
    doLast {
        project.layout.buildDirectory.dir("generated/sources/resourceObjects").get().asFile.deleteRecursively()
    }
}
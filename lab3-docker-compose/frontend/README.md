# ShareCare
Мобильное приложение для проекта по "Обучению служением"

[Figma](https://www.figma.com/design/Db1CRT5ioyXSXzdg5cTi1W/%D0%9E%D0%B1%D1%83%D1%87%D0%B5%D0%BD%D0%B8%D0%B5-%D1%81%D0%BB%D1%83%D0%B6%D0%B5%D0%BD%D0%B8%D0%B5%D0%BC-%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82?node-id=3-6166&t=7XwpxeZ7Q2haBsWN-0) [Скриншоты](images)
>[!NOTE]
>«Добродар» – платформа в формате **мобильного приложения**,​ предназначенная​ для поддержки социально уязвимых категорий граждан: малообеспеченных семей,​ одиноких пенсионеров, людей в трудной жизненной ситуации – **на основе системы​ безвозмездного обмена с автоматизацией создания объявлений с помощью**

### Поддерживаемые платформы:
- iOS (18.2+)
- Android (13+)
>Поддержка Android 12 и ниже **возможна**, если добавить fallback'и к блюру

# QuickStart
1) Поставить [сервер](https://github.com/ShareCare-MAI-project/Backend) _(без него не получится пройти регистрацию и т.п.)_
2) Импортировать проект в Android Studio или IntelliJ IDEA
3) Сбилдить проект: 
    - composeApp для Android и других платформ
    - iosApp для iOS
> Для билда лучше всего пользоваться [плагином KMP](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)

>[!CAUTION]
>iOS можно сбилдить только на MacOS


# Техническая информация
### Стек:
>В основе стека лежит [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html), т.к. это новый тренд в Android-разработке
- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html): Язык
- [Compose Multiplatform](https://www.jetbrains.com/ru-ru/compose-multiplatform/): Кросплатформенный UI
- [Decompose](https://github.com/arkivanov/Decompose): Библиотека для навигации и архитектуры компонентного подхода
- [Ktor](https://ktor.io/): Запросы в сеть
- [Koin](https://insert-koin.io/): Dependency Injection
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings): Хранение пар ключ-значение (обёртка над SharedPreferences и NSUserDefaults)
- [Coil](https://github.com/coil-kt/coil): Асинхронная загрузка картинок
- [Haze](https://chrisbanes.github.io/haze/latest/): Мультиплатформенный блюр :+1:
- Kotlinx: Coroutines, Serialization, DateTime

### Архитектура:
>[!IMPORTANT]
>В проекте представлена **MVVM** архитектура с DDD подходом (Domain-Data-Presentation)
<img width="4483" height="1604" alt="arc" src="https://github.com/user-attachments/assets/fed30a9a-c903-4a98-bcfe-d91d5c17b7bd" />


> WIP по Readme: сделать нормальные скрины, добавить гифки/видео

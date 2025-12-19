
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object FontSizeManager {
    private val _fontSize: MutableStateFlow<Float> = MutableStateFlow(1f)
    val fontSize = _fontSize.asStateFlow()

    fun setFontSize(value: Float) {
        _fontSize.value = value
    }
}
package com.github.jdha.codedivider.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

enum class Case {
    UPPER,
    LOWER,
    TITLE,
    NONE
}

enum class Position {
    LEFT,
    CENTER,
    Right
}

enum class CommentSymbolType {
    ONE_SINGLE_LINE,
    TWO_SINGLE_LINE,
    TWO_MULTI_LINE
}

enum class BoxType {
    NORMAL,
    ROUNDED,
    HEAVY,
    NORMAL_OPEN_TOP,
    ROUNDED_OPEN_TOP,
}

@Service
@State(name = "CodeDividerSettings", storages = [Storage("CodeDividerSettings.xml")])
class CodeDividerSettingsState : PersistentStateComponent<CodeDividerSettingsState> {

    // LINE
    var lineLength: Int = 88
    var textCase: Case = Case.TITLE
    var whiteSpacePadCommentSymbol: Boolean = true
    var customLineChar: String = "─"
    var commentSymbolType: CommentSymbolType = CommentSymbolType.ONE_SINGLE_LINE

    // BOX
    var maxBoxLength: Int = 88

    companion object {
        val instance: CodeDividerSettingsState
            get() =
                ApplicationManager.getApplication().getService(CodeDividerSettingsState::class.java)
    }

    @Nullable override fun getState() = this

    override fun loadState(state: CodeDividerSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }
}

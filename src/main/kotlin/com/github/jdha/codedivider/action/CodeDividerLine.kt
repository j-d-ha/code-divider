package com.github.jdha.codedivider.action

import com.github.jdha.codedivider.settings.CodeDividerSettingsState
import com.github.jdha.codedivider.settings.CommentSymbolType
import com.github.jdha.codedivider.settings.TextCase
import com.github.jdha.codedivider.settings.TextPosition
import com.intellij.lang.Commenter
import com.intellij.lang.LanguageCommenters
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import java.util.*

// ━━ SAMPLE LINES ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

// Normal Lines
// ── Normal Left ──────────────────────────────────────────────────────────────────────
// ─────────────────────────────────── Normal Center ───────────────────────────────────
// ───────────────────────────────────────────────────────────────────── Normal Right ──

// Heavy Lines
// ━━ HEAVY LEFT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ HEAVY CENTER ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ HEAVY RIGHT ━━

// Double Lines
// ══ Double Left ══════════════════════════════════════════════════════════════════════
// ═══════════════════════════════════ Double Center ═══════════════════════════════════
// ═════════════════════════════════════════════════════════════════════ Double Right ══

// ━━ CONSTANTS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

// General
const val SPACE = " "
const val EMPTY = ""

// ━━ ACTIONS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

// Standard
class CodeDividerLineTextLeftStandard : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.LEFT,
            lineCar = settings.lineCharStandard,
            textCase = settings.textCaseStandard,
        )
    }
}

class CodeDividerLineTextCenterStandard : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.CENTER,
            lineCar = settings.lineCharStandard,
            textCase = settings.textCaseStandard,
        )
    }
}

class CodeDividerLineTextRightStandard : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.Right,
            lineCar = settings.lineCharStandard,
            textCase = settings.textCaseStandard,
        )
    }
}

// Heavy
class CodeDividerLineTextLeftHeavy : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.LEFT,
            lineCar = settings.lineCharHeavy,
            textCase = settings.textCaseHeavy,
        )
    }
}

class CodeDividerLineTextCenterHeavy : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.CENTER,
            lineCar = settings.lineCharHeavy,
            textCase = settings.textCaseHeavy,
        )
    }
}

class CodeDividerLineTextRightHeavy : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.Right,
            lineCar = settings.lineCharHeavy,
            textCase = settings.textCaseHeavy,
        )
    }
}

// Double
class CodeDividerLineTextLeftDouble : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.LEFT,
            lineCar = settings.lineCharDouble,
            textCase = settings.textCaseDouble,
        )
    }
}

class CodeDividerLineTextCenterDouble : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.CENTER,
            lineCar = settings.lineCharDouble,
            textCase = settings.textCaseDouble,
        )
    }
}

class CodeDividerLineTextRightDouble : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val settings = CodeDividerSettingsState.instance
        codeDivider(
            e = e,
            settings = settings,
            textPosition = TextPosition.Right,
            lineCar = settings.lineCharDouble,
            textCase = settings.textCaseDouble,
        )
    }
}

// ━━ LOGIC ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

fun codeDivider(
    e: AnActionEvent,
    settings: CodeDividerSettingsState,
    textPosition: TextPosition,
    lineCar: String,
    textCase: TextCase
) {
    // ── Setup Values Needed To Make Line ─────────────────────────────────────────────────────────
    val editor = e.getData(CommonDataKeys.EDITOR) ?: return

    val document = editor.document
    val caretModel = editor.caretModel
    val caretOffset = caretModel.offset

    val lineNumber = document.getLineNumber(caretOffset)
    val startOffset = document.getLineStartOffset(lineNumber)
    val endOffset = document.getLineEndOffset(lineNumber)
    val lineText = document.getText(com.intellij.openapi.util.TextRange(startOffset, endOffset))

    val language = e.getData(CommonDataKeys.PSI_FILE)?.language ?: return

    // ── Comment Symbols ──────────────────────────────────────────────────────────────────────────
    val commentPad = if (settings.whiteSpacePadCommentSymbolLine) SPACE else EMPTY

    val commenter = LanguageCommenters.INSTANCE.forLanguage(language)
    val (commentPrefix, commentSuffix) =
        getCommentSymbols(
            commenter = commenter,
            errorOnNoCommentSymbol = settings.errorOnNoCommentSymbol,
            commentPad = commentPad,
            commentSymbolType = settings.commentSymbolTypeLine,
        )

    // ── Existing Text ────────────────────────────────────────────────────────────────────────────
    val indentLevel = lineText.takeWhile { it.isWhitespace() }.length
    var existingText = lineText.dropWhile { it.isWhitespace() }.trim()

    val lineStart = SPACE.repeat(indentLevel) + commentPrefix
    var lineBody = EMPTY

    // ── Blank Line ───────────────────────────────────────────────────────────────────────────────
    if (existingText.isBlank()) {
        val remainingLength = settings.lineLengthLine - lineStart.length - commentSuffix.length
        if (remainingLength > 0) {
            lineBody = lineCar.repeat(remainingLength)
        }
        WriteCommandAction.runWriteCommandAction(e.project) {
            val line = lineStart + lineBody + commentSuffix
            document.replaceString(startOffset, endOffset, line)
        }
        // move caret to end of line
        caretModel.moveToOffset(document.getLineEndOffset(caretModel.logicalPosition.line))
        return
    }

    // ── Not Blank Line ───────────────────────────────────────────────────────────────────────────
    existingText =
        when (textCase) {
            TextCase.UPPER -> existingText.uppercase()
            TextCase.LOWER -> existingText.lowercase()
            TextCase.TITLE ->
                existingText.split(SPACE).joinToString(SPACE) {
                    it.lowercase().replaceFirstChar { innerIt ->
                        if (innerIt.isLowerCase()) innerIt.titlecase(Locale.getDefault())
                        else innerIt.toString()
                    }
                }
            TextCase.NONE -> existingText
        }

    when (textPosition) {
        TextPosition.LEFT -> {
            val prefixLineCharCount = if (settings.whiteSpacePadCommentSymbolLine) 2 else 3
            lineBody = lineCar.repeat(prefixLineCharCount) + SPACE + existingText + SPACE
            val remainingLength =
                settings.lineLengthLine - lineStart.length - lineBody.length - commentSuffix.length
            if (remainingLength > 0) {
                lineBody += lineCar.repeat(remainingLength)
            }
        }
        TextPosition.CENTER -> {
            lineBody = SPACE + existingText + SPACE
            val remainingLength =
                settings.lineLengthLine - lineStart.length - lineBody.length - commentSuffix.length
            if (remainingLength > 0) {
                val leftPadding = remainingLength / 2
                val rightPadding = remainingLength - leftPadding
                lineBody = lineCar.repeat(leftPadding) + lineBody + lineCar.repeat(rightPadding)
            }
        }
        TextPosition.Right -> {
            val suffixLineCharCount = if (settings.whiteSpacePadCommentSymbolLine) 2 else 3
            lineBody = SPACE + existingText + SPACE + lineCar.repeat(suffixLineCharCount)
            val remainingLength =
                settings.lineLengthLine - lineStart.length - lineBody.length - commentSuffix.length
            if (remainingLength > 0) {
                lineBody = lineCar.repeat(remainingLength) + lineBody
            }
        }
    }

    WriteCommandAction.runWriteCommandAction(e.project) {
        val line = lineStart + lineBody + commentSuffix
        document.replaceString(startOffset, endOffset, line)
    }
    // move caret to end of line
    caretModel.moveToOffset(document.getLineEndOffset(caretModel.logicalPosition.line))
}

// ━━ HELPERS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

fun getCommentSymbols(
    commenter: Commenter,
    errorOnNoCommentSymbol: Boolean,
    commentPad: String,
    commentSymbolType: CommentSymbolType
): Pair<String, String> {
    // some languages like xml don't have lineCommentPrefix; some like python don't have
    // blockCommentPrefix. We use the elvis operator to handle this and check if any value is null
    val lineCommentPrefix = commenter.lineCommentPrefix?.trim()
    val blockCommentPrefix = commenter.blockCommentPrefix?.trim()
    val blockCommentSuffix = commenter.blockCommentSuffix?.trim()

    // some files like .txt files wont have comment symbols
    if (errorOnNoCommentSymbol && lineCommentPrefix == null && blockCommentPrefix == null)
        throw Exception("No comment prefix found")

    val commentPrefix =
        when (commentSymbolType) {
            CommentSymbolType.ONE_SINGLE_LINE,
            CommentSymbolType.TWO_SINGLE_LINE ->
                (lineCommentPrefix ?: blockCommentPrefix) + commentPad
            CommentSymbolType.TWO_MULTI_LINE ->
                (blockCommentPrefix ?: lineCommentPrefix) + commentPad
        }

    val commentSuffix =
        when (commentSymbolType) {
            CommentSymbolType.ONE_SINGLE_LINE ->
                if (lineCommentPrefix != null) EMPTY else commentPad + blockCommentSuffix
            CommentSymbolType.TWO_SINGLE_LINE ->
                commentPad + (lineCommentPrefix ?: blockCommentSuffix)
            CommentSymbolType.TWO_MULTI_LINE ->
                commentPad + (blockCommentSuffix ?: lineCommentPrefix)
        }

    return Pair(commentPrefix, commentSuffix)
}

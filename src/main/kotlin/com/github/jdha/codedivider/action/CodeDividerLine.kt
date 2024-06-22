package com.github.jdha.codedivider.action

import com.github.jdha.codedivider.settings.*
import com.intellij.lang.Commenter
import com.intellij.lang.LanguageCommenters
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import java.util.*

// Examples

// Normal Lines
// ── Normal Left ──────────────────────────────────────────────────────────────────────
// ─────────────────────────────────── Normal Center ───────────────────────────────────
// ────────────────────────────────────────────────────────────────────── Normal Right──

// Heavy Lines
// ══ HEAVY LEFT ═══════════════════════════════════════════════════════════════════════
// ═══════════════════════════════════ HEAVY CENTER ════════════════════════════════════
// ═══════════════════════════════════════════════════════════════════════ HEAVY RIGHT══

// General
const val SPACE = " "
const val EMPTY = ""

// ══ Normal ═══════════════════════════════════════════════════════════════════════════════════════

class CodeDividerLineTextLeftNormal : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, TextPosition.LEFT, CodeDividerSettingsState.instance.normalLineSettings)
    }
}

class CodeDividerLineTextCenterNormal : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, TextPosition.CENTER, CodeDividerSettingsState.instance.normalLineSettings)
    }
}

class CodeDividerLineTextRightNormal : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, TextPosition.Right, CodeDividerSettingsState.instance.normalLineSettings)
    }
}

// ══ Heavy ════════════════════════════════════════════════════════════════════════════════════════

class CodeDividerLineTextLeftHeavy : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, TextPosition.LEFT, CodeDividerSettingsState.instance.heavyLineSettings)
    }
}

class CodeDividerLineTextCenterHeavy : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, TextPosition.CENTER, CodeDividerSettingsState.instance.heavyLineSettings)
    }
}

class CodeDividerLineTextRightHeavy : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, TextPosition.Right, CodeDividerSettingsState.instance.heavyLineSettings)
    }
}

// ══ Logic ════════════════════════════════════════════════════════════════════════════════════════

fun codeDivider(e: AnActionEvent, textPosition: TextPosition, lineSettings: LineSettings) {
    // ── Setup Values Needed To Make Line ─────────────────────────────────────────────────────────
    val editor = e.getData(CommonDataKeys.EDITOR) ?: return

    // val settings = CodeDividerSettingsState.instance

    val document = editor.document
    val caretModel = editor.caretModel
    val caretOffset = caretModel.offset

    val lineNumber = document.getLineNumber(caretOffset)
    val startOffset = document.getLineStartOffset(lineNumber)
    val endOffset = document.getLineEndOffset(lineNumber)
    val lineText = document.getText(com.intellij.openapi.util.TextRange(startOffset, endOffset))

    val language = e.getData(CommonDataKeys.PSI_FILE)?.language ?: return

    // ── Comment Symbols ──────────────────────────────────────────────────────────────────────────
    val commentPad = if (lineSettings.whiteSpacePadCommentSymbol) SPACE else EMPTY

    val commenter = LanguageCommenters.INSTANCE.forLanguage(language)
    val (commentPrefix, commentSuffix) =
        getCommentSymbols(
            commenter = commenter,
            errorOnNoCommentSymbol = lineSettings.whiteSpacePadCommentSymbol,
            commentPad = commentPad,
            commentSymbolType = lineSettings.commentSymbolType,
        )

    // ── Existing Text ────────────────────────────────────────────────────────────────────────────
    val indentLevel = lineText.takeWhile { it.isWhitespace() }.length
    var existingText = lineText.dropWhile { it.isWhitespace() }.trim()

    val lineStart = SPACE.repeat(indentLevel) + commentPrefix
    var lineBody = EMPTY

    // ── Blank Line ───────────────────────────────────────────────────────────────────────────────
    if (existingText.isBlank()) {
        val remainingLength = lineSettings.lineLength - lineStart.length - commentSuffix.length
        if (remainingLength > 0) {
            lineBody = lineSettings.customLineChar.repeat(remainingLength)
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
        when (lineSettings.textCase) {
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
            val prefixLineCharCount = if (lineSettings.whiteSpacePadCommentSymbol) 2 else 3
            lineBody =
                lineSettings.customLineChar.repeat(prefixLineCharCount) +
                    SPACE +
                    existingText +
                    SPACE
            val remainingLength =
                lineSettings.lineLength - lineStart.length - lineBody.length - commentSuffix.length
            if (remainingLength > 0) {
                lineBody += lineSettings.customLineChar.repeat(remainingLength)
            }
        }
        TextPosition.CENTER -> {
            lineBody = SPACE + existingText + SPACE
            val remainingLength =
                lineSettings.lineLength - lineStart.length - lineBody.length - commentSuffix.length
            if (remainingLength > 0) {
                val leftPadding = remainingLength / 2
                val rightPadding = remainingLength - leftPadding
                lineBody =
                    lineSettings.customLineChar.repeat(leftPadding) +
                        lineBody +
                        lineSettings.customLineChar.repeat(rightPadding)
            }
        }
        TextPosition.Right -> {
            val suffixLineCharCount = if (lineSettings.whiteSpacePadCommentSymbol) 2 else 3
            lineBody =
                SPACE + existingText + lineSettings.customLineChar.repeat(suffixLineCharCount)
            val remainingLength =
                lineSettings.lineLength - lineStart.length - lineBody.length - commentSuffix.length
            if (remainingLength > 0) {
                lineBody = lineSettings.customLineChar.repeat(remainingLength) + lineBody
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

// ══ HELPERS ══════════════════════════════════════════════════════════════════════════════════════

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

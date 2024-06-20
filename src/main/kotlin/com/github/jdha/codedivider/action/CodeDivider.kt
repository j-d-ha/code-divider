package com.github.jdha.codedivider.action

import com.github.jdha.codedivider.settings.Case
import com.github.jdha.codedivider.settings.CodeDividerSettingsState
import com.github.jdha.codedivider.settings.CommentSymbolType
import com.github.jdha.codedivider.settings.Position
import com.intellij.lang.LanguageCommenters
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import java.util.*

// ── Left ────────────────────────────────────────────────────────────────────────── //

// ───────────────────────────────────── Center ───────────────────────────────────── //

// ───────────────────────────────────────────────────────────────────────── Right ── //

class CodeDividerLineTextLeft : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, Position.LEFT)
    }
}

class CodeDividerLineTextCenter : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, Position.CENTER)
    }
}

class CodeDividerLineTextRight : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        codeDivider(e, Position.Right)
    }
}

fun codeDivider(e: AnActionEvent, position: Position) {
    // ── Setup Values Needed To Make Line ─────────────────────────────────────────────────────────
    val editor = e.getData(CommonDataKeys.EDITOR) ?: return

    val settings = CodeDividerSettingsState.instance

    val document = editor.document
    val caretModel = editor.caretModel
    val caretOffset = caretModel.offset

    val lineNumber = document.getLineNumber(caretOffset)
    val startOffset = document.getLineStartOffset(lineNumber)
    val endOffset = document.getLineEndOffset(lineNumber)
    val lineText = document.getText(com.intellij.openapi.util.TextRange(startOffset, endOffset))

    val language = e.getData(CommonDataKeys.PSI_FILE)?.language ?: return

    // ── Comment Symbols ──────────────────────────────────────────────────────────────────────────
    val commentPad = if (settings.whiteSpacePadCommentSymbol) " " else ""

    val commenter = LanguageCommenters.INSTANCE.forLanguage(language)
    val lineCommentPrefix =
        when (settings.commentSymbolType) {
            CommentSymbolType.ONE_SINGLE_LINE,
            CommentSymbolType.TWO_SINGLE_LINE -> commenter.lineCommentPrefix + commentPad
            CommentSymbolType.TWO_MULTI_LINE -> commenter.blockCommentPrefix + commentPad
        }

    val lineCommentSuffix =
        when (settings.commentSymbolType) {
            CommentSymbolType.ONE_SINGLE_LINE -> ""
            CommentSymbolType.TWO_SINGLE_LINE -> commentPad + commenter.lineCommentPrefix
            CommentSymbolType.TWO_MULTI_LINE -> commentPad + commenter.blockCommentSuffix
        }

    val indentLevel = lineText.takeWhile { it.isWhitespace() }.length
    var existingText = lineText.dropWhile { it.isWhitespace() }.trim()

    val lineStart = " ".repeat(indentLevel) + lineCommentPrefix
    var lineBody = ""

    // ── Blank Line ───────────────────────────────────────────────────────────────────────────────
    if (existingText.isBlank()) {
        val remainingLength = settings.lineLength - lineStart.length - lineCommentSuffix.length
        if (remainingLength > 0) {
            lineBody = settings.customLineChar.repeat(remainingLength)
        }
        WriteCommandAction.runWriteCommandAction(e.project) {
            document.replaceString(startOffset, endOffset, lineStart + lineBody + lineCommentSuffix)
        }
        return
    }

    // ── Line Not Blank ───────────────────────────────────────────────────────────────────────────
    existingText =
        when (settings.textCase) {
            Case.UPPER -> existingText.uppercase()
            Case.LOWER -> existingText.lowercase()
            Case.TITLE ->
                existingText.split(" ").joinToString(" ") {
                    it.lowercase().replaceFirstChar { innerIt ->
                        if (innerIt.isLowerCase()) innerIt.titlecase(Locale.getDefault())
                        else innerIt.toString()
                    }
                }
            Case.NONE -> existingText
        }

    when (position) {
        Position.LEFT -> {
            val prefixLineCharCount = if (settings.whiteSpacePadCommentSymbol) 2 else 3
            lineBody = "${settings.customLineChar.repeat(prefixLineCharCount)} $existingText "
            val remainingLength =
                settings.lineLength - lineStart.length - lineBody.length - lineCommentSuffix.length
            if (remainingLength > 0) {
                lineBody += settings.customLineChar.repeat(remainingLength)
            }
        }
        Position.CENTER -> {
            lineBody = " $existingText "
            val remainingLength =
                settings.lineLength - lineStart.length - lineBody.length - lineCommentSuffix.length
            if (remainingLength > 0) {
                val leftPadding = remainingLength / 2
                val rightPadding = remainingLength - leftPadding
                lineBody =
                    settings.customLineChar.repeat(leftPadding) +
                        lineBody +
                        settings.customLineChar.repeat(rightPadding)
            }
        }
        Position.Right -> {
            val suffixLineCharCount = if (settings.whiteSpacePadCommentSymbol) 2 else 3
            lineBody = " $existingText ${settings.customLineChar.repeat(suffixLineCharCount)}"
            val remainingLength =
                settings.lineLength - lineStart.length - lineBody.length - lineCommentSuffix.length
            if (remainingLength > 0) {
                lineBody = settings.customLineChar.repeat(remainingLength) + lineBody
            }
        }
    }

    WriteCommandAction.runWriteCommandAction(e.project) {
        document.replaceString(startOffset, endOffset, lineStart + lineBody + lineCommentSuffix)
    }
}

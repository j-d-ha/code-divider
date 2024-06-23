package com.github.jdha.codedivider.action

import com.github.jdha.codedivider.settings.BoxType
import com.github.jdha.codedivider.settings.CodeDividerSettingsState
import com.github.jdha.codedivider.settings.CommentSymbolType
import com.intellij.lang.LanguageCommenters
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import kotlin.math.max
import kotlin.math.min

// ━━ SAMPLE BOXES ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

//      ┌──────────────────────────────────────────────────────────┐
//      │                   normal, single line                    │
//      └──────────────────────────────────────────────────────────┘

//      ┌──────────────────────────────────────────────────────────┐
//      │  normal, multi line                                      │
//      │  Lorem ipsum dolor sit amet, consectetur adipiscing      │
//      │  elit, sed do eiusmod tempor incididunt ut labore et     │
//      │  dolore magna aliqua.                                    │
//      └──────────────────────────────────────────────────────────┘

//      ╭──────────────────────────────────────────────────────────╮
//      │                       box, rounded                       │
//      ╰──────────────────────────────────────────────────────────╯

//      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
//      ┃                        box, heavy                        ┃
//      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

//      ╔══════════════════════════════════════════════════════════╗
//      ║                     box, double line                     ║
//      ╚══════════════════════════════════════════════════════════╝

//      ┌─                                                        ─┐
//      │                      box, open top                       │
//      └─                                                        ─┘

//      ╭─                                                        ─╮
//      │                  box, open top, rounded                  │
//      ╰─                                                        ─╯

//      ┏━                                                        ━┓
//      ┃                   box, open top, heavy                   ┃
//      ┗━                                                        ━┛

//      ╔═                                                        ═╗
//      ║                box, open top, double line                ║
//      ╚═                                                        ═╝

//      ┌──────────────────────────────────────────────────────────┐
//                             box, open side
//      └──────────────────────────────────────────────────────────┘

//      ╭──────────────────────────────────────────────────────────╮
//                        box, open side, rounded
//      ╰──────────────────────────────────────────────────────────╯

//      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
//                         box, open side, heavy
//      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

//      ╔══════════════════════════════════════════════════════════╗
//                      box, open side, double line
//      ╚══════════════════════════════════════════════════════════╝

// ━━ LINE CONSTANTS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

// Normal
const val NORMAL_HORIZONTAL = "─"
const val NORMAL_VERTICAL = "│"
const val NORMAL_CORNER_TL = "┌"
const val NORMAL_CORNER_TR = "┐"
const val NORMAL_CORNER_BL = "└"
const val NORMAL_CORNER_BR = "┘"

// Rounded
const val ROUNDED_HORIZONTAL = NORMAL_HORIZONTAL
const val ROUNDED_VERTICAL = NORMAL_VERTICAL
const val ROUNDED_CORNER_TL = "╭"
const val ROUNDED_CORNER_TR = "╮"
const val ROUNDED_CORNER_BL = "╰"
const val ROUNDED_CORNER_BR = "╯"

// Heavy
const val HEAVY_HORIZONTAL = "━"
const val HEAVY_VERTICAL = "┃"
const val HEAVY_CORNER_TL = "┏"
const val HEAVY_CORNER_TR = "┓"
const val HEAVY_CORNER_BL = "┗"
const val HEAVY_CORNER_BR = "┛"

// Double
const val DOUBLE_HORIZONTAL = "═"
const val DOUBLE_VERTICAL = "║"
const val DOUBLE_CORNER_TL = "╔"
const val DOUBLE_CORNER_TR = "╗"
const val DOUBLE_CORNER_BL = "╚"
const val DOUBLE_CORNER_BR = "╝"

// ━━ ACTIONS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

// solid side
class CodeDividerBoxStandard : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.NORMAL)
    }
}

class CodeDividerBoxRounded : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.ROUNDED)
    }
}

class CodeDividerBoxHeavy : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.HEAVY)
    }
}

class CodeDividerBoxDouble : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.DOUBLE)
    }
}

// open top
class CodeDividerBoxNormalOpenTop : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.NORMAL_OPEN_TOP)
    }
}

class CodeDividerBoxRoundedOpenTop : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.ROUNDED_OPEN_TOP)
    }
}

class CodeDividerBoxHeavyOpenTop : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.HEAVY_OPEN_TOP)
    }
}

class CodeDividerBoxDoubleOpenTop : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.DOUBLE_OPEN_TOP)
    }
}

// open side
class CodeDividerBoxNormalOpenSide : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.NORMAL_OPEN_SIDE)
    }
}

class CodeDividerBoxRoundedOpenSide : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.ROUNDED_OPEN_SIDE)
    }
}

class CodeDividerBoxHeavyOpenSide : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.HEAVY_OPEN_SIDE)
    }
}

class CodeDividerBoxDoubleOpenSide : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.DOUBLE_OPEN_SIDE)
    }
}

// ━━ LOGIC ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

fun normalBox(e: AnActionEvent, boxType: BoxType) {
    // ── General Setup ────────────────────────────────────────────────────────────────────────────
    val editor = e.getData(CommonDataKeys.EDITOR) ?: return

    val settings = CodeDividerSettingsState.instance

    val document = editor.document
    val caretModel = editor.caretModel
    val caretOffset = caretModel.offset
    val selectionModel = editor.selectionModel

    val lineNumber = document.getLineNumber(caretOffset)
    val lineStartOffset = document.getLineStartOffset(lineNumber)
    val lineEndOffset = document.getLineEndOffset(lineNumber)
    val lineText = document.getText(TextRange(lineStartOffset, lineEndOffset))

    val startOffset = min(lineStartOffset, selectionModel.selectionStart)
    val endOffset = max(lineEndOffset, selectionModel.selectionEnd)

    val language = e.getData(CommonDataKeys.PSI_FILE)?.language ?: return

    // ── Comment Symbols ──────────────────────────────────────────────────────────────────────────
    val (commentPrefix, commentSuffix) =
        getCommentSymbols(
            commenter = LanguageCommenters.INSTANCE.forLanguage(language),
            errorOnNoCommentSymbol = settings.errorOnNoCommentSymbol,
            commentPad = SPACE,
            commentSymbolType = CommentSymbolType.ONE_SINGLE_LINE,
        )

    // ── Existing Text ────────────────────────────────────────────────────────────────────────────
    val indentLevel = lineText.takeWhile { it.isWhitespace() }.length

    // get currently selected text.
    val existingText =
        editor.selectionModel.selectedText ?: lineText.dropWhile { it.isWhitespace() }.trim()

    // ── Set Box Parts ────────────────────────────────────────────────────────────────────────────
    val vertical: String
    val horizontal: String
    val cornerTL: String
    val cornerTR: String
    val cornerBL: String
    val cornerBR: String

    when (boxType) {
        // solid side
        BoxType.NORMAL -> {
            vertical = NORMAL_VERTICAL
            horizontal = NORMAL_HORIZONTAL
            cornerTL = NORMAL_CORNER_TL
            cornerTR = NORMAL_CORNER_TR
            cornerBL = NORMAL_CORNER_BL
            cornerBR = NORMAL_CORNER_BR
        }

        BoxType.ROUNDED -> {
            vertical = ROUNDED_VERTICAL
            horizontal = ROUNDED_HORIZONTAL
            cornerTL = ROUNDED_CORNER_TL
            cornerTR = ROUNDED_CORNER_TR
            cornerBL = ROUNDED_CORNER_BL
            cornerBR = ROUNDED_CORNER_BR
        }

        BoxType.HEAVY -> {
            vertical = HEAVY_VERTICAL
            horizontal = HEAVY_HORIZONTAL
            cornerTL = HEAVY_CORNER_TL
            cornerTR = HEAVY_CORNER_TR
            cornerBL = HEAVY_CORNER_BL
            cornerBR = HEAVY_CORNER_BR
        }

        BoxType.DOUBLE -> {
            vertical = DOUBLE_VERTICAL
            horizontal = DOUBLE_HORIZONTAL
            cornerTL = DOUBLE_CORNER_TL
            cornerTR = DOUBLE_CORNER_TR
            cornerBL = DOUBLE_CORNER_BL
            cornerBR = DOUBLE_CORNER_BR
        }

        // open top
        BoxType.NORMAL_OPEN_TOP -> {
            vertical = NORMAL_VERTICAL
            horizontal = SPACE
            cornerTL = NORMAL_CORNER_TL + NORMAL_HORIZONTAL
            cornerTR = NORMAL_HORIZONTAL + NORMAL_CORNER_TR
            cornerBL = NORMAL_CORNER_BL + NORMAL_HORIZONTAL
            cornerBR = NORMAL_HORIZONTAL + NORMAL_CORNER_BR
        }

        BoxType.ROUNDED_OPEN_TOP -> {
            vertical = ROUNDED_VERTICAL
            horizontal = SPACE
            cornerTL = ROUNDED_CORNER_TL + ROUNDED_HORIZONTAL
            cornerTR = ROUNDED_HORIZONTAL + ROUNDED_CORNER_TR
            cornerBL = ROUNDED_CORNER_BL + ROUNDED_HORIZONTAL
            cornerBR = ROUNDED_HORIZONTAL + ROUNDED_CORNER_BR
        }

        BoxType.HEAVY_OPEN_TOP -> {
            vertical = HEAVY_VERTICAL
            horizontal = SPACE
            cornerTL = HEAVY_CORNER_TL + HEAVY_HORIZONTAL
            cornerTR = HEAVY_HORIZONTAL + HEAVY_CORNER_TR
            cornerBL = HEAVY_CORNER_BL + HEAVY_HORIZONTAL
            cornerBR = HEAVY_HORIZONTAL + HEAVY_CORNER_BR
        }

        BoxType.DOUBLE_OPEN_TOP -> {
            vertical = DOUBLE_VERTICAL
            horizontal = SPACE
            cornerTL = DOUBLE_CORNER_TL + DOUBLE_HORIZONTAL
            cornerTR = DOUBLE_HORIZONTAL + DOUBLE_CORNER_TR
            cornerBL = DOUBLE_CORNER_BL + DOUBLE_HORIZONTAL
            cornerBR = DOUBLE_HORIZONTAL + DOUBLE_CORNER_BR
        }

        // open side
        BoxType.NORMAL_OPEN_SIDE -> {
            vertical = SPACE
            horizontal = NORMAL_HORIZONTAL
            cornerTL = NORMAL_CORNER_TL
            cornerTR = NORMAL_CORNER_TR
            cornerBL = NORMAL_CORNER_BL
            cornerBR = NORMAL_CORNER_BR
        }

        BoxType.ROUNDED_OPEN_SIDE -> {
            vertical = SPACE
            horizontal = ROUNDED_HORIZONTAL
            cornerTL = ROUNDED_CORNER_TL
            cornerTR = ROUNDED_CORNER_TR
            cornerBL = ROUNDED_CORNER_BL
            cornerBR = ROUNDED_CORNER_BR
        }

        BoxType.HEAVY_OPEN_SIDE -> {
            vertical = SPACE
            horizontal = HEAVY_HORIZONTAL
            cornerTL = HEAVY_CORNER_TL
            cornerTR = HEAVY_CORNER_TR
            cornerBL = HEAVY_CORNER_BL
            cornerBR = HEAVY_CORNER_BR
        }

        BoxType.DOUBLE_OPEN_SIDE -> {
            vertical = SPACE
            horizontal = DOUBLE_HORIZONTAL
            cornerTL = DOUBLE_CORNER_TL
            cornerTR = DOUBLE_CORNER_TR
            cornerBL = DOUBLE_CORNER_BL
            cornerBR = DOUBLE_CORNER_BR
        }
    }

    // ── Make Box ─────────────────────────────────────────────────────────────────────────────────
    val postCommentSymbolPadding = SPACE.repeat(settings.paddingSpaceAfterCommentSymbolBox)
    val lineStart = SPACE.repeat(indentLevel) + commentPrefix + postCommentSymbolPadding
    val postCommentIndent =
        if (commentSuffix.isNotEmpty()) postCommentSymbolPadding + commentSuffix else EMPTY

    val linePad = SPACE.repeat(settings.paddingInsideBox)
    var fillLength = settings.targetLengthBox - (horizontal.length * 2) - (linePad.length * 2)

    val box =
        buildString {
                val textList =
                    if (existingText.length >= settings.targetLengthBox ||
                        existingText.contains("\n")) {
                        buildList {
                            fillLength =
                                existingText.split(Regex(pattern = "\\s")).fold(fillLength) {
                                    acc,
                                    word ->
                                    max(acc, word.length)
                                }
                            val addLine = { line: String ->
                                add(line + SPACE.repeat(max(fillLength - line.length, 0)))
                            }
                            existingText.split("\n").forEach { line ->
                                var newLine = ""
                                line.split(SPACE).forEach { word ->
                                    val tempLine = "$newLine $word".trim()
                                    if (tempLine.length > fillLength) {
                                        addLine(newLine)
                                        newLine = word
                                    } else {
                                        newLine = tempLine
                                    }
                                }
                                if (newLine.isNotEmpty()) {
                                    addLine(newLine)
                                }
                            }
                        }
                    } else {
                        val remainingLength = fillLength - existingText.length
                        val left = remainingLength / 2
                        val right = remainingLength - left
                        listOf(SPACE.repeat(left) + existingText + SPACE.repeat(right))
                    }

                val topStraightLength =
                    fillLength + (linePad.length * 2) + (vertical.length * 2) -
                        cornerTL.length -
                        cornerTR.length

                appendLine(
                    lineStart +
                        cornerTL +
                        horizontal.repeat(topStraightLength) +
                        cornerTR +
                        postCommentIndent,
                )

                textList.forEach {
                    appendLine(
                        lineStart +
                            vertical +
                            linePad +
                            it +
                            linePad +
                            vertical +
                            postCommentIndent,
                    )
                }

                appendLine(
                    lineStart +
                        cornerBL +
                        horizontal.repeat(topStraightLength) +
                        cornerBR +
                        postCommentIndent,
                )
            }
            .trimEnd()

    // ── Write Output ─────────────────────────────────────────────────────────────────────────────
    WriteCommandAction.runWriteCommandAction(e.project) {
        document.replaceString(startOffset, endOffset, box)
    }

    // move caret to end of line
    caretModel.moveToOffset(startOffset + box.length)
}

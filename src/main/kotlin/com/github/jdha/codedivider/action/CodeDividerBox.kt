package com.github.jdha.codedivider.action

import com.github.jdha.codedivider.settings.BoxType
import com.github.jdha.codedivider.settings.CodeDividerSettingsState
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

//      ┌──────────────────────────────────────┐
//      │ Lorem ipsumdolor sit amet,           │
//      │  consectetur adipiscing elit, sed    │
//      │  do eiusmod tempor incididunt...     │
//      └──────────────────────────────────────┘

//      ╭─────────────────────────────────────────────────────────╮
//      │                        COMMANDS                         │
//      │                        COMMANDS                         │
//      │                        COMMANDS                         │
//      ╰─────────────────────────────────────────────────────────╯

//      ╔══════════════════════════════════════╗
//      ║             BOXES & LINES            ║
//      ╚══════════════════════════════════════╝

//      ┌                                      ┐
//      │                 BOXES                │
//      └                                      ┘

//      ╭─                                                       ─╮
//      │                        COMMANDS                         │
//      ╰─                                                       ─╯

// ── Line Constants ───────────────────────────────────────────────────────────────────────────────

// General
const val SPACE = " "

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
const val HEAVY_HORIZONTAL = "═"
const val HEAVY_VERTICAL = "║"
const val HEAVY_CORNER_TL = "╔"
const val HEAVY_CORNER_TR = "╗"
const val HEAVY_CORNER_BL = "╚"
const val HEAVY_CORNER_BR = "╝"

class CodeDividerBox : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        normalBox(e, BoxType.NORMAL)
    }
}

fun normalBox(e: AnActionEvent, boxType: BoxType) {
    // ── General Setup ────────────────────────────────────────────────────────────────────────
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

    // ── Box ──────────────────────────────────────────────────────────────────────────────────

    // ── Box, Heavy ───────────────────────────────────────────────────────────────────────────

    // ── Box, Rounded Corners ─────────────────────────────────────────────────────────────────

    // ── Box, Open ────────────────────────────────────────────────────────────────────────────

    // ── Box, Open, Rounded Corners ───────────────────────────────────────────────────────────
}

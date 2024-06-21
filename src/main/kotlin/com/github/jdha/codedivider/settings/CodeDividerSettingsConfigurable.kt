package com.github.jdha.codedivider.settings

import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JTextField

class CodeDividerSettingsConfigurable : BoundConfigurable("Code Divider") {
    private var settings = CodeDividerSettingsState.instance

    override fun createPanel(): DialogPanel {
        return panel {
            // ══ LINE ═════════════════════════════════════════════════════════════════════════════
            collapsibleGroup("Code Divider - Line") {
                    // ── Normal Line ──────────────────────────────────────────────────────────────
                    group("Normal Line") {
                        row("Line length") {
                            spinner(40..300).bindIntValue(settings.normalLineSettings::lineLength)
                        }

                        row("Text case") {
                            comboBox(TextCase.values().asList())
                                .bindItem(
                                    settings.normalLineSettings::textCase.toNullableProperty())
                        }

                        row("Whitespace pad comment symbol") {
                            checkBox("")
                                .bindSelected(
                                    settings.normalLineSettings::whiteSpacePadCommentSymbol)
                        }

                        row("Error on no comment symbol for language") {
                            checkBox("")
                                .bindSelected(settings.normalLineSettings::errorOnNoCommentSymbol)
                        }

                        row("Custom line character") {
                            comboBox(listOf("─"))
                                .applyToComponent {
                                    isEditable = true
                                    editor.editorComponent.addKeyListener(
                                        object : KeyAdapter() {
                                            override fun keyTyped(e: KeyEvent) {
                                                val textField = e.source as JTextField
                                                if (textField.text.isNotEmpty()) {
                                                    e.consume()
                                                }
                                            }
                                        })
                                }
                                .bindItem(
                                    settings.normalLineSettings::customLineChar
                                        .toNullableProperty())
                                .columns(5)
                        }

                        buttonsGroup(title = "Comment Symbol Type") {
                                row {
                                    radioButton(
                                        "Single line comment symbol, start only",
                                        CommentSymbolType.ONE_SINGLE_LINE)
                                }
                                row {
                                    radioButton(
                                        "Single line comment symbol, start and end",
                                        CommentSymbolType.TWO_SINGLE_LINE)
                                }
                                row {
                                    radioButton(
                                        "Multi line comment symbol, start and end",
                                        CommentSymbolType.TWO_MULTI_LINE)
                                }
                            }
                            .bind(settings.normalLineSettings::commentSymbolType)
                    }

                    // ── Heavy Line ───────────────────────────────────────────────────────────────
                    group("Heavy Line") {
                        row("Line length") {
                            spinner(40..300).bindIntValue(settings.heavyLineSettings::lineLength)
                        }

                        row("Text case") {
                            comboBox(TextCase.values().asList())
                                .bindItem(settings.heavyLineSettings::textCase.toNullableProperty())
                        }

                        row("Whitespace pad comment symbol") {
                            checkBox("")
                                .bindSelected(
                                    settings.heavyLineSettings::whiteSpacePadCommentSymbol)
                        }

                        row("Error on no comment symbol for language") {
                            checkBox("")
                                .bindSelected(settings.heavyLineSettings::errorOnNoCommentSymbol)
                        }

                        row("Custom line character") {
                            comboBox(listOf("═"))
                                .applyToComponent {
                                    isEditable = true
                                    editor.editorComponent.addKeyListener(
                                        object : KeyAdapter() {
                                            override fun keyTyped(e: KeyEvent) {
                                                val textField = e.source as JTextField
                                                if (textField.text.isNotEmpty()) {
                                                    e.consume()
                                                }
                                            }
                                        })
                                }
                                .bindItem(
                                    settings.heavyLineSettings::customLineChar.toNullableProperty())
                                .columns(5)
                        }

                        buttonsGroup(title = "Comment Symbol Type") {
                                row {
                                    radioButton(
                                        "Single line comment symbol, start only",
                                        CommentSymbolType.ONE_SINGLE_LINE)
                                }
                                row {
                                    radioButton(
                                        "Single line comment symbol, start and end",
                                        CommentSymbolType.TWO_SINGLE_LINE)
                                }
                                row {
                                    radioButton(
                                        "Multi line comment symbol, start and end",
                                        CommentSymbolType.TWO_MULTI_LINE)
                                }
                            }
                            .bind(settings.heavyLineSettings::commentSymbolType)
                    }
                }
                .expanded = true

            // ══ BOX ══════════════════════════════════════════════════════════════════════════════
            collapsibleGroup("Code Divider - Box") {
                    row("Box max length") { spinner(40..300).bindIntValue(settings::maxBoxLength) }
                }
                .expanded = true
        }
    }
}

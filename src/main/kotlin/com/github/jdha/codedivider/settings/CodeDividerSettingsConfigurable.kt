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
            // ━━ GENERAL ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            collapsibleGroup("General") {
                    row("Error on no comment symbol for language") {
                        checkBox("").bindSelected(settings::errorOnNoCommentSymbol)
                    }
                }
                .expanded = true

            // ━━ LINE ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            collapsibleGroup("Lines") {
                    // ── General ──────────────────────────────────────────────────────────────────
                    row("Line length") { spinner(40..300).bindIntValue(settings::lineLengthLine) }

                    row("Whitespace pad comment symbol") {
                        checkBox("").bindSelected(settings::whiteSpacePadCommentSymbolLine)
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
                        .bind(settings::commentSymbolTypeLine)

                    // ── Standard Line ────────────────────────────────────────────────────────────
                    group("Standard Line") {
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
                                .bindItem(settings::lineCharStandard.toNullableProperty())
                                .columns(5)
                        }

                        row("Text case transformation") {
                            comboBox(TextCase.values().asList())
                                .bindItem(settings::textCaseStandard.toNullableProperty())
                        }
                    }

                    // ── Heavy Line ───────────────────────────────────────────────────────────────
                    group("Heavy Line") {
                        row("Custom line character") {
                            comboBox(listOf("━"))
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
                                .bindItem(settings::lineCharHeavy.toNullableProperty())
                                .columns(5)
                        }

                        row("Text case transformation") {
                            comboBox(TextCase.values().asList())
                                .bindItem(settings::textCaseHeavy.toNullableProperty())
                        }
                    }

                    // ── Double Line ──────────────────────────────────────────────────────────────
                    group("Double Line") {
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
                                .bindItem(settings::lineCharDouble.toNullableProperty())
                                .columns(5)
                        }

                        row("Text case transformation") {
                            comboBox(TextCase.values().asList())
                                .bindItem(settings::textCaseDouble.toNullableProperty())
                        }
                    }
                }
                .expanded = true

            // ━━ BOX ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            collapsibleGroup("Boxes") {
                    row("Box target length") {
                        spinner(40..300).bindIntValue(settings::targetLengthBox)
                    }

                    row("Padding spaces after comment symbol") {
                        spinner(0..100).bindIntValue(settings::paddingSpaceAfterCommentSymbolBox)
                    }

                    row("Padding inside box before and after text") {
                        spinner(0..100).bindIntValue(settings::paddingInsideBox)
                    }

                    row("Text case transformation") {
                        comboBox(TextCase.values().asList())
                            .bindItem(settings::textCaseBox.toNullableProperty())
                    }
                }
                .expanded = true
        }
    }
}

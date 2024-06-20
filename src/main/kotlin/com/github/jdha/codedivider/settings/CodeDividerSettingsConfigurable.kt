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
            group("Code Divider - Line") {
                row("Line length") { spinner(40..300).bindIntValue(settings::lineLength) }

                row("Text case") {
                    comboBox(Case.values().asList())
                        .bindItem(settings::textCase.toNullableProperty())
                }

                row("Whitespace pad comment symbol") {
                    checkBox("").bindSelected(settings::whiteSpacePadCommentSymbol)
                }

                row("Custom line character") {
                    textField().bindText(settings::customLineChar).columns(2).apply {
                        component.addKeyListener(
                            object : KeyAdapter() {
                                override fun keyTyped(e: KeyEvent) {
                                    val textField = e.source as JTextField
                                    if (textField.text.isNotEmpty()) {
                                        e.consume()
                                    }
                                }
                            })
                    }
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
                    .bind(settings::commentSymbolType)
            }

            group("Code Divider - Box") {
                row("Box max length") { spinner(40..300).bindIntValue(settings::maxBoxLength) }
            }
        }
    }
}

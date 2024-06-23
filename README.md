# Code Divider

![Build](https://github.com/j-d-ha/Code-Divider/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/24705.svg)](https://plugins.jetbrains.com/plugin/24705)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/24705.svg)](https://plugins.jetbrains.com/plugin/24705)

<!-- Plugin description -->

## Description

This is a plugin that lets you add dividers to your code using comments. Diver types include lines and boxes. Sample
dividers are shown below.

Lines:

```text
Standard Lines
── Standard Left ────────────────────────────────────────────────────────────────────
────────────────────────────────── Standard Center ──────────────────────────────────
─────────────────────────────────────────────────────────────────── Standard Right ──

Heavy Lines
━━ HEAVY LEFT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ HEAVY CENTER ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ HEAVY RIGHT ━━

Double Lines
══ Double Left ══════════════════════════════════════════════════════════════════════
═══════════════════════════════════ Double Center ═══════════════════════════════════
═════════════════════════════════════════════════════════════════════ Double Right ══
```

Boxes:

```text
┌──────────────────────────────────────────────────────────┐
│                   normal, single line                    │
└──────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────┐
│  normal, multi line                                      │
│  Lorem ipsum dolor sit amet, consectetur adipiscing      │
│  elit, sed do eiusmod tempor incididunt ut labore et     │
│  dolore magna aliqua.                                    │
└──────────────────────────────────────────────────────────┘

╭──────────────────────────────────────────────────────────╮
│                       box, rounded                       │
╰──────────────────────────────────────────────────────────╯

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃                        box, heavy                        ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

╔══════════════════════════════════════════════════════════╗
║                     box, double line                     ║
╚══════════════════════════════════════════════════════════╝

┌─                                                        ─┐
│                      box, open top                       │
└─                                                        ─┘

╭─                                                        ─╮
│                  box, open top, rounded                  │
╰─                                                        ─╯

┏━                                                        ━┓
┃                   box, open top, heavy                   ┃
┗━                                                        ━┛

╔═                                                        ═╗
║                box, open top, double line                ║
╚═                                                        ═╝

┌──────────────────────────────────────────────────────────┐
                      box, open side
└──────────────────────────────────────────────────────────┘

╭──────────────────────────────────────────────────────────╮
                 box, open side, rounded
╰──────────────────────────────────────────────────────────╯

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
                  box, open side, heavy
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

╔══════════════════════════════════════════════════════════╗
               box, open side, double line
╚══════════════════════════════════════════════════════════╝

```

## Usage

It is recommended to access code dividers by searching `Code Divider` in `Find Action` . This can be done by
pressing `Ctrl + Shift + A` and
typing `Code Divider`. This will bring up a list of all available dividers. Selecting one will insert it at the current
cursor position. Alternatively, selecting `Code Divider` will bring up a menu of all available dividers.

## Settings

Most parts of `Code Divider` can be customized. This includes lengths, text transformation, and line symbol types. These
settings can be accessed in <kbd>Settings/Preferences</kbd> > <kbd>Code Divider</kbd>.

<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "code-divider"</kbd> >
  <kbd>Install</kbd>

- Manually:

  Download the [latest release](https://github.com/j-d-ha/code-divider/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---

## Acknowledgements

This theme is heavily inspired by [comment-box.nvim](https://github.com/LudoPinelli/comment-box.nvim).

## Issues and Bugs

Please submit issues with any bugs you find. 

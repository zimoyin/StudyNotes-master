## Compose Multiplatform 与 Swing 的集成

## 涵盖什么

在本教程中，我们将向您展示如何使`Swing/Compose`互操作在您的应用程序中工作、它的局限性是什么、您可以使用它实现什么、在什么情况下可以使用它以及什么时候不应该这样做。

Compose Multiplatform 和 Swing 之间互操作性的主要目标是

- 使 Swing 应用程序迁移到 Compose 变得更容易、更顺畅
- 允许使用没有“Compose”类似物的 Swing 组件来增强 Compose 应用程序

在许多情况下，在 Compose 中实现缺少的组件（并将其贡献给社区）比在 Compose 应用程序中使用 Swing 组件更有效。

## Swing 互操作用例和限制

在组合 Compose Multiplatform 和 Swing 之前，请务必记住这两种技术具有不同的内容呈现方法。Compose Multiplatform 使用一个重量级 Swing 组件来渲染其所有内容，并具有逻辑渲染层，而 Swing 既可以在重量级组件上运行，也可以在轻量级组件上运行 ( `Component/JComponent`)。对于 Swing 逻辑来说，Compose Multiplatform 只是一个重量级组件，它与它的交互方式与所有其他 Swing 组件的交互方式相同。

第一个用例是将 Compose 部分添加到 Swing 应用程序中。可以使用`ComposePanel`Swing 组件来呈现应用程序的“Compose”部分。从 Swing 的角度来看，它只是另一个 Swing 组件，应该进行相应的处理。重要的一点是，所有 Compose 组件都将在 中呈现`ComposePanel`，包括弹出窗口、工具提示、上下文菜单等。它们将在`ComposePanel`. 因此，最好将它们替换为基于 Swing 的实现。

`ComposePanel`您可以在下面找到几种合理使用的情况：

- 您想要将动画对象或整个动画对象面板嵌入到您的应用程序中（选择表情符号、对事件进行动画反应的工具栏等）
- 您想要在应用程序中实现交互式渲染区域，使用 Compose 可以更轻松、更方便地实现（例如，任何类型的图形或信息图表）
- 您想要将复杂的渲染区域（甚至可能是动画的）嵌入到您的应用程序中 - 使用 Compose 可以更轻松、更方便地做到这一点
- 您想要替换基于 Swing 的应用程序的用户界面的复杂部分 - Compose 具有方便的组件布局系统，并且 Compose 提供了广泛的内置组件和选项，用于快速创建您自己的组件

如果您的情况与上述情况之一有些相似，那么您应该尝试使用 来实现它`ComposePanel`。

第二个用例是当您想要使用 Swing 中存在的某些组件而 Compose 中没有类似组件时的情况。从头开始创建它的成本太高。在这种情况下，您可以使用`SwingPanel`. A`SwingPanel`是一个包装器，用于控制放置在 Compose 多平台组件之上的 Swing 组件的大小、位置和呈现，这意味着 a 内的组件`SwingPanel`将始终位于 Compose 的深度之上。任何放错位置并停留在 上的东西`SwingPanel`都会被放置在那里的 Swing 组件剪裁，因此请尝试考虑这些情况，如果存在这种风险，那么最好相应地重新设计 UI，或者停止使用`SwingPanel`和仍然尝试实现缺失的组件，从而为技术的发展做出贡献，并使其他开发人员的生活变得更轻松。

`SwingPanel`您可以在下面找到几种合理使用的情况：

- 您的应用程序中没有弹出窗口、工具提示、上下文菜单等。或者它们没有在您的内部使用`SwingPanel`
- 在您的应用程序中，`SwingPanel`始终处于相同位置。这将减少更改 Swing 组件位置时出现故障和伪影的风险（此条件不是强制性的，您需要单独测试每个此类情况）

如果您的情况与上述情况之一有些相似，那么您应该尝试使用 来实现它`SwingPanel`。

由于 Compose Multiplatform 和 Swing 可以双向组合，因此很可能将 a 放入`SwingPanel`a 中`ComposePanel`，而 a 又可以放入另一个 中`SwingPanel`。在这种情况下，您应该小心，尽量减少渲染故障。在本教程的最后，您可以找到涵盖此案例的示例。

## ComposePanel

`ComposePanel`允许您在基于 Swing 的 UI 中使用 Compose Multiplatform 创建 UI。为了实现这一点，您需要创建 的一个实例`ComposePanel`，将其添加到您的 Swing 布局中，并描述 内部的组成`setContent`。

```kotlin
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

val northClicks = mutableStateOf(0)
val westClicks = mutableStateOf(0)
val eastClicks = mutableStateOf(0)

fun main() = SwingUtilities.invokeLater {
    val window = JFrame()

    // creating ComposePanel
    val composePanel = ComposePanel()
    window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    window.title = "SwingComposeWindow"

    window.contentPane.add(actionButton("NORTH", action = { northClicks.value++ }), BorderLayout.NORTH)
    window.contentPane.add(actionButton("WEST", action = { westClicks.value++ }), BorderLayout.WEST)
    window.contentPane.add(actionButton("EAST", action = { eastClicks.value++ }), BorderLayout.EAST)
    window.contentPane.add(
        actionButton(
            text = "SOUTH/REMOVE COMPOSE",
            action = {
                window.contentPane.remove(composePanel)
            }
        ),
        BorderLayout.SOUTH
    )

    // addind ComposePanel on JFrame
    window.contentPane.add(composePanel, BorderLayout.CENTER)

    // setting the content
    composePanel.setContent {
        ComposeContent()
    }

    window.setSize(800, 600)
    window.isVisible = true
}

fun actionButton(text: String, action: () -> Unit): JButton {
    val button = JButton(text)
    button.toolTipText = "Tooltip for $text button."
    button.preferredSize = Dimension(100, 100)
    button.addActionListener { action() }
    return button
}

@Composable
fun ComposeContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Counter("West", westClicks)
            Spacer(modifier = Modifier.width(25.dp))
            Counter("North", northClicks)
            Spacer(modifier = Modifier.width(25.dp))
            Counter("East", eastClicks)
        }
    }
}

@Composable
fun Counter(text: String, counter: MutableState<Int>) {
    Surface(
        modifier = Modifier.size(130.dp, 130.dp),
        color = Color(180, 180, 180),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier.height(30.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "${text}Clicks: ${counter.value}")
            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { counter.value++ }) {
                    Text(text = text, color = Color.White)
                }
            }
        }
    }
}
```



![与Swing集成](%E4%B8%8ESwing%E4%BA%92%E6%93%8D%E4%BD%9C.assets/screenshot.png)

## 使用Swing 组件

**使用 SwingPanel 将 Swing 组件添加到 Compose Multiplatform 组合中**

SwingPanel 允许您在基于 Compose 的 UI 中使用 Swing 创建 UI。要实现此目的，您需要在的参数`JComponent`中创建 Swing 。`factory``SwingPanel`

```kotlin
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import java.awt.Component
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel

fun main() = singleWindowApplication {
    val counter = remember { mutableStateOf(0) }

    val inc: () -> Unit = { counter.value++ }
    val dec: () -> Unit = { counter.value-- }

    Box(
        modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Counter: ${counter.value}")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(top = 80.dp, bottom = 20.dp)
        ) {
            Button("1. Compose Button: increment", inc)
            Spacer(modifier = Modifier.height(20.dp))

            SwingPanel(
                background = Color.White,
                modifier = Modifier.size(270.dp, 90.dp),
                factory = {
                    JPanel().apply {
                        layout = BoxLayout(this, BoxLayout.Y_AXIS)
                        add(actionButton("1. Swing Button: decrement", dec))
                        add(actionButton("2. Swing Button: decrement", dec))
                        add(actionButton("3. Swing Button: decrement", dec))
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
            Button("2. Compose Button: increment", inc)
        }
    }
}

@Composable
fun Button(text: String = "", action: (() -> Unit)? = null) {
    Button(
        modifier = Modifier.size(270.dp, 30.dp),
        onClick = { action?.invoke() }
    ) {
        Text(text)
    }
}

fun actionButton(
    text: String,
    action: () -> Unit
): JButton {
    val button = JButton(text)
    button.alignmentX = Component.CENTER_ALIGNMENT
    button.addActionListener { action() }

    return button
}
```



[![与Swing集成](https://github.com/JetBrains/compose-multiplatform/raw/master/tutorials/Swing_Integration/swing_panel.gif)

`SwingPanel`下面的示例显示了当可组合状态更改时如何更新 Swing 组件。为此，您需要提供一个`update: (T) -> Unit`回调，该回调在可组合状态更改或布局膨胀后调用。

```kotlin
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JLabel

val swingLabel = JLabel()

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 400.dp, height = 200.dp),
    ) {
        val clicks = remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SwingPanel(
                modifier = Modifier.fillMaxWidth().height(40.dp),
                factory = {
                    JPanel().apply {
                        add(swingLabel, BorderLayout.CENTER)
                    }
                },
                update = {
                    swingLabel.setText("SwingLabel Clicks: ${clicks.value}")
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row (
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { clicks.value++ }) {
                    Text(text = "Increment")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = { clicks.value-- }) {
                    Text(text = "Decrement")
                }
            }
        }
    }
}
```



[![与Swing集成](%E4%B8%8ESwing%E4%BA%92%E6%93%8D%E4%BD%9C.assets/swing_panel_update.gif)

## SwingPanel

**使用 SwingPanel 和 ComposePanel 进行布局**

下面的示例显示了如何将 Compose for Desktop 和 Swing 双向组合，即将 a 添加`SwingPanel`到 a `ComposePanel`，然后将 a 添加到另一个`SwingPanel`。

```kotlin
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.awt.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.*
import androidx.compose.ui.unit.*
import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Dimension
import java.awt.Insets
import java.awt.event.*
import javax.swing.*
import javax.swing.border.EmptyBorder

val Gray = java.awt.Color(64, 64, 64)
val DarkGray = java.awt.Color(32, 32, 32)
val LightGray = java.awt.Color(210, 210, 210)

data class Item(
    val text: String,
    val icon: ImageVector,
    val color: Color,
    val state: MutableState<Boolean> = mutableStateOf(false)
)
val panelItemsList = listOf(
    Item(text = "Person", icon = Icons.Filled.Person, color = Color(10, 232, 162)),
    Item(text = "Favorite", icon = Icons.Filled.Favorite, color = Color(150, 232, 150)),
    Item(text = "Search", icon = Icons.Filled.Search, color = Color(232, 10, 162)),
    Item(text = "Settings", icon = Icons.Filled.Settings, color = Color(232, 162, 10)),
    Item(text = "Close", icon = Icons.Filled.Close, color = Color(232, 100, 100))
)
val itemSize = 50.dp

fun java.awt.Color.toCompose(): Color {
    return Color(red, green, blue)
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 500.dp, height = 500.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = Gray.toCompose()).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Compose Area", color = LightGray.toCompose())
            Spacer(modifier = Modifier.height(40.dp))
            SwingPanel(
                background = DarkGray.toCompose(),
                modifier = Modifier.fillMaxSize(),
                factory = {
                    ComposePanel().apply {
                        setContent {
                            Box {
                                SwingPanel(
                                    modifier = Modifier.fillMaxSize(),
                                    factory = { SwingComponent() }
                                )
                                Box (
                                    modifier = Modifier.align(Alignment.TopStart)
                                        .padding(start = 20.dp, top = 80.dp)
                                        .background(color = DarkGray.toCompose())
                                ) {
                                    SwingPanel(
                                        modifier = Modifier.size(itemSize * panelItemsList.size, itemSize),
                                        factory = {
                                            ComposePanel().apply {
                                                setContent {
                                                    ComposeOverlay()
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

fun SwingComponent() : JPanel {
    return JPanel().apply {
        background = DarkGray
        border = EmptyBorder(20, 20, 20, 20)
        layout = BorderLayout()
        add(
            JLabel("TextArea Swing Component").apply {
                foreground = LightGray
                verticalAlignment = SwingConstants.NORTH
                horizontalAlignment = SwingConstants.CENTER
                preferredSize = Dimension(40, 160)
            },
            BorderLayout.NORTH
        )
        add(
            JTextArea().apply {
                background = LightGray
                lineWrap = true
                wrapStyleWord = true
                margin = Insets(10, 10, 10, 10)
                text = "The five boxing wizards jump quickly. " +
                "Crazy Fredrick bought many very exquisite opal jewels. " +
                "Pack my box with five dozen liquor jugs.\n" +
                "Cozy sphinx waves quart jug of bad milk. " +
                "The jay, pig, fox, zebra and my wolves quack!"
            },
            BorderLayout.CENTER
        )
    }
}

@Composable
fun ComposeOverlay() {
    Box(
        modifier = Modifier.fillMaxSize().
            background(color = DarkGray.toCompose()),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.background(
                shape = RoundedCornerShape(4.dp),
                color = Color.DarkGray.copy(alpha = 0.5f)
            )
        ) {
            for (item in panelItemsList) {
                SelectableItem(
                    text = item.text,
                    icon = item.icon,
                    color = item.color,
                    selected = item.state
                )
            }
        }
    }
}

@Composable
fun SelectableItem(
    text: String,
    icon: ImageVector,
    color: Color,
    selected: MutableState<Boolean>
) {
    Box(
        modifier = Modifier.size(itemSize)
            .clickable { selected.value = !selected.value },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.alpha(if (selected.value) 1.0f else 0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(modifier = Modifier.size(32.dp), imageVector = icon, contentDescription = null, tint = color)
            Text(text = text, color = Color.White, fontSize = 10.sp)
        }
    }
}
```



[![与Swing集成](https://github.com/JetBrains/compose-multiplatform/raw/master/tutorials/Swing_Integration/swing_compose_layouting.gif)
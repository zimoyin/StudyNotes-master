# Gio

Gio 是一个用 Go 编写跨平台即时模式 GUI 的库。Gio 支持所有主要平台：Linux、macOS、Windows、Android、iOS、FreeBSD、OpenBSD 和 WebAssembly。

Gio 的设计目标是只需很少的依赖即可运行。它仅依赖于平台库来进行窗口管理、输入和 GPU 绘制。

Gio 可帮助 Go 开发人员在所有主要平台上构建高效、流畅且可移植的 GUI。它将尖端的 2D 图形技术与即时模式图形范例的灵活性相结合，为应用程序开发创建了引人注目且一致的基础。

Gio 包含一个基于在OpenGL ES 和 Direct3D 11 上实现的[Pathfinder 项目的高效矢量渲染器，并且正在向基于](https://github.com/servo/pathfinder)[piet-gpu](https://github.com/linebender/piet-gpu)构建的更高效的基于计算着色器的渲染器迁移。文本和其他形状仅使用其轮廓进行渲染，而无需将其烘焙到纹理图像中，以支持高效的动画、转换绘图和像素分辨率独立性。



## 运行Demo

```sh
go run gioui.org/example/hello@latest
```



## 引入依赖

请查阅官方版本号与模块: https://pkg.go.dev/gioui.org#section-readme

```go
require gioui.org v0.6.0

require (
	gioui.org/cpu v0.0.0-20210817075930-8d6a761490d2 // indirect
	gioui.org/shader v1.0.8 // indirect
	github.com/go-text/typesetting v0.1.1 // indirect
	github.com/go-text/typesetting-utils v0.0.0-20240329101916-eee87fb235a3 // indirect
	golang.org/x/exp v0.0.0-20221012211006-4de253d81b95 // indirect
	golang.org/x/exp/shiny v0.0.0-20220827204233-334a2380cb91 // indirect
	golang.org/x/image v0.5.0 // indirect
	golang.org/x/sys v0.7.0 // indirect
	golang.org/x/text v0.9.0 // indirect
)
```

## 开始第一个程序

> 可以不引入依赖



1. 初始化一个项目

```shell
go mod init gio.test
```

2. 编写代码

```go
package main

import (
	"gioui.org/app"
	"gioui.org/op"
	"gioui.org/text"
	"gioui.org/widget/material"
	"image/color"
	"log"
	"os"
)

func main() {
	go start()
	app.Main()
}
func start() {
	window := new(app.Window)
	err := run(window)
	if err != nil {
		log.Fatal(err)
	}
	os.Exit(0)
}

func run(window *app.Window) error {
	// 创建新的主题: 应用程序需要定义其字体和不同的颜色设置。主题包含所有必要的信息。主题只针对使用该主题的组件
	theme := material.NewTheme()
	var ops op.Ops

	// Gio 是事件驱动刷新的，当事件发生的时候才会通知刷新
	for {
		// 监听事件
		switch e := window.Event().(type) {
		// 与操作系统（即键盘、鼠标、GPU）的通信通过事件进行。Gio 使用以下方法来处理事件
		case app.DestroyEvent: // 销毁事件: 表示用户按下了关闭按钮。
			return e.Err
		case app.FrameEvent: // 帧(刷新绘制)事件: 意味着程序应该处理输入并渲染一个新的帧。
			//--------------//
			// 绘制文本的流程 //
			// -------------//

			// 此图形上下文用于管理渲染状态。
			gtx := app.NewContext(&ops, e)

			// 用适当的文本定义一个大标签：
			title := material.H1(theme, "你好")

			// 更改标签的颜色。
			maroon := color.NRGBA{R: 127, G: 0, B: 0, A: 255}
			title.Color = maroon

			// 更改标签的位置。
			title.Alignment = text.Middle

			// 根据图形上下文绘制标签。
			title.Layout(gtx)

			// 将绘图操作传递给GPU。
			e.Frame(gtx.Ops)
		}
	}
}

```

3. 更新依赖

```sh
go mod tidy
```

4. 运行

```sh
go run .
```



## 分割组件

### 根据需求定制

有时候需要编写自定义的组件或布局。

为了实现子组件的渲染，我们可以使用如下代码：

```
type SplitVisual struct{}

func (s SplitVisual) Layout(gtx layout.Context, left, right layout.Widget) layout.Dimensions {
	leftsize := gtx.Constraints.Min.X / 2
	rightsize := gtx.Constraints.Min.X - leftsize

	{
		gtx := gtx
		gtx.Constraints = layout.Exact(image.Pt(leftsize, gtx.Constraints.Max.Y))
		left(gtx)
	}

	{
		gtx := gtx
		gtx.Constraints = layout.Exact(image.Pt(rightsize, gtx.Constraints.Max.Y))
		trans := op.Offset(image.Pt(leftsize, 0)).Push(gtx.Ops)
		right(gtx)
		trans.Pop()
	}

	return layout.Dimensions{Size: gtx.Constraints.Max}
}
```

然后我们可以像这样使用该组件：

```
func exampleSplitVisual(gtx layout.Context, th *material.Theme) layout.Dimensions {
	return SplitVisual{}.Layout(gtx, func(gtx layout.Context) layout.Dimensions {
		return FillWithLabel(gtx, th, "Left", red)
	}, func(gtx layout.Context) layout.Dimensions {
		return FillWithLabel(gtx, th, "Right", blue)
	})
}

func FillWithLabel(gtx layout.Context, th *material.Theme, text string, backgroundColor color.NRGBA) layout.Dimensions {
	ColorBox(gtx, gtx.Constraints.Max, backgroundColor)
	return layout.Center.Layout(gtx, material.H3(th, text).Layout)
}
```

### 调整比例

让我们使比例可调整。在这种情况下，0 可以表示居中分割。

```
type SplitRatio struct {
	// Ratio 保存当前布局。
	// 0 表示居中，-1 表示完全向左，1 表示完全向右。
	Ratio float32
}

func (s SplitRatio) Layout(gtx layout.Context, left, right layout.Widget) layout.Dimensions {
	proportion := (s.Ratio + 1) / 2
	leftsize := int(proportion * float32(gtx.Constraints.Max.X))

	rightoffset := leftsize
	rightsize := gtx.Constraints.Max.X - rightoffset

	{
		gtx := gtx
		gtx.Constraints = layout.Exact(image.Pt(leftsize, gtx.Constraints.Max.Y))
		left(gtx)
	}

	{
		trans := op.Offset(image.Pt(rightoffset, 0)).Push(gtx.Ops)
		gtx := gtx
		gtx.Constraints = layout.Exact(image.Pt(rightsize, gtx.Constraints.Max.Y))
		right(gtx)
		trans.Pop()
	}

	return layout.Dimensions{Size: gtx.Constraints.Max}
}
```

使用代码如下：

```
func exampleSplitRatio(gtx layout.Context, th *material.Theme) layout.Dimensions {
	return SplitRatio{Ratio: -0.3}.Layout(gtx, func(gtx layout.Context) layout.Dimensions {
		return FillWithLabel(gtx, th, "Left", red)
	}, func(gtx layout.Context) layout.Dimensions {
		return FillWithLabel(gtx, th, "Right", blue)
	})
}
```

### 交互

为了使其更有用，我们可以使分割条可以拖动。

因为我们还需要一个区域用于移动分割条，让我们在中心添加一个条：

```
bar := gtx.Dp(s.Bar)
if bar <= 1 {
	bar = gtx.Dp(defaultBarWidth)
}

proportion := (s.Ratio + 1) / 2
leftsize := int(proportion*float32(gtx.Constraints.Max.X) - float32(bar))

rightoffset := leftsize + bar
rightsize := gtx.Constraints.Max.X - rightoffset
```

现在我们需要存储交互状态：

```
type Split struct {
	// Ratio 保存当前布局。
	// 0 表示居中，-1 表示完全向左，1 表示完全向右。
	Ratio float32
	// Bar 是调整布局的宽度
	Bar unit.Dp

	drag   bool
	dragID pointer.ID
	dragX  float32
}
```

然后我们需要处理输入事件：

```
barRect := image.Rect(leftsize, 0, rightoffset, gtx.Constraints.Max.X)
area := clip.Rect(barRect).Push(gtx.Ops)

// 注册输入
event.Op(gtx.Ops, s)
pointer.CursorColResize.Add(gtx.Ops)

for {
	ev, ok := gtx.Event(pointer.Filter{
		Target: s,
		Kinds:  pointer.Press | pointer.Drag | pointer.Release | pointer.Cancel,
	})
	if !ok {
		break
	}

	e, ok := ev.(pointer.Event)
	if !ok {
		continue
	}

	switch e.Kind {
	case pointer.Press:
		if s.drag {
			break
		}

		s.dragID = e.PointerID
		s.dragX = e.Position.X
		s.drag = true

	case pointer.Drag:
		if s.dragID != e.PointerID {
			break
		}

		deltaX := e.Position.X - s.dragX
		s.dragX = e.Position.X

		deltaRatio := deltaX * 2 / float32(gtx.Constraints.Max.X)
		s.Ratio += deltaRatio

		if e.Priority < pointer.Grabbed {
			gtx.Execute(pointer.GrabCmd{
				Tag: s,
				ID:  s.dragID,
			})
		}

	case pointer.Release:
		fallthrough
	case pointer.Cancel:
		s.drag = false
	}
}

area.Pop()
```

### 结果

将整个组件整合起来：

```
type Split struct {
	// Ratio 保存当前布局。
	// 0 表示居中，-1 表示完全向左，1 表示完全向右。
	Ratio float32
	// Bar 是调整布局的宽度
	Bar unit.Dp

	drag   bool
	dragID pointer.ID
	dragX  float32
}

const defaultBarWidth = unit.Dp(10)

func (s *Split) Layout(gtx layout.Context, left, right layout.Widget) layout.Dimensions {
	bar := gtx.Dp(s.Bar)
	if bar <= 1 {
		bar = gtx.Dp(defaultBarWidth)
	}

	proportion := (s.Ratio + 1) / 2
	leftsize := int(proportion*float32(gtx.Constraints.Max.X) - float32(bar))

	rightoffset := leftsize + bar
	rightsize := gtx.Constraints.Max.X - rightoffset

	{ // 处理输入
		barRect := image.Rect(leftsize, 0, rightoffset, gtx.Constraints.Max.X)
		area := clip.Rect(barRect).Push(gtx.Ops)

		// 注册输入
		event.Op(gtx.Ops, s)
		pointer.CursorColResize.Add(gtx.Ops)

		for {
			ev, ok := gtx.Event(pointer.Filter{
				Target: s,
				Kinds:  pointer.Press | pointer.Drag | pointer.Release | pointer.Cancel,
			})
			if !ok {
				break
			}

			e, ok := ev.(pointer.Event)
			if !ok {
				continue
			}

			switch e.Kind {
			case pointer.Press:
				if s.drag {
					break
				}

				s.dragID = e.PointerID
				s.dragX = e.Position.X
				s.drag = true

			case pointer.Drag:
				if s.dragID != e.PointerID {
					break
				}

				deltaX := e.Position.X - s.dragX
				s.dragX = e.Position.X

				deltaRatio := deltaX * 2 / float32(gtx.Constraints.Max.X)
				s.Ratio += deltaRatio

				if e.Priority < pointer.Grabbed {
					gtx.Execute(pointer.GrabCmd{
						Tag: s,
						ID:  s.dragID,
					})
				}

			case pointer.Release:
				fallthrough
			case pointer.Cancel:
				s.drag = false
			}
		}

		area.Pop()
	}

	{
		gtx := gtx
		gtx.Constraints = layout.Exact(image.Pt(leftsize, gtx.Constraints.Max.Y))
		left(gtx)
	}

	{
		off := op.Offset(image.Pt(rightoffset, 0)).Push(gtx.Ops)
		gtx := gtx
		gtx.Constraints = layout.Exact(image.Pt(rightsize, gtx.Constraints.Max.Y))
		right(gtx)
		off.Pop()
	}

	return layout.Dimensions{Size: gtx.Constraints.Max}
}
```

一个示例：

```
var split Split

func exampleSplit(gtx layout.Context, th *material.Theme) layout.Dimensions {
	return split.Layout(gtx, func(gtx layout.Context) layout.Dimensions {
		return FillWithLabel(gtx, th, "Left", red)
	}, func(gtx layout.Context) layout.Dimensions {
		return FillWithLabel(gtx, th, "Right", blue)
	})
}
```

### 总结

在本文中，我们介绍了如何创建一个分割组件，从简单的静态布局开始，逐步添加比例调整功能，并最终实现一个可拖动的分割条组件。通过这些步骤，我们可以创建一个功能丰富且用户友好的自定义组件。

### 代码解释

```go
// Split 结构体定义了一个分割组件
type Split struct {
    // Ratio 表示分割比例，范围是 -1 到 1
    Ratio  float32
    // Bar 表示分割条的宽度
    Bar    unit.Dp
    // drag 标记是否正在拖动分割条
    drag   bool
    // dragID 是当前拖动的指针ID
    dragID pointer.ID
    // dragX 是拖动时的X坐标
    dragX  float32
}

// defaultBarWidth 定义默认的分割条宽度
const defaultBarWidth = unit.Dp(10)

// Layout 方法布局分割组件
func (s *Split) Layout(gtx layout.Context, left, right layout.Widget) layout.Dimensions {
    // 获取分割条的实际宽度，如果 Bar <= 1，则使用默认宽度
    bar := gtx.Dp(s.Bar)
    if bar <= 1 {
        bar = gtx.Dp(defaultBarWidth)
    }

    // 根据比例计算左侧和右侧的宽度
    proportion := (s.Ratio + 1) / 2
    leftsize := int(proportion*float32(gtx.Constraints.Max.X) - float32(bar))
    rightoffset := leftsize + bar
    rightsize := gtx.Constraints.Max.X - rightoffset

    { // 处理输入事件
        // 定义分割条的矩形区域
        barRect := image.Rect(leftsize, 0, rightoffset, gtx.Constraints.Max.X)
        // 将矩形区域添加到操作列表中
        area := clip.Rect(barRect).Push(gtx.Ops)

        // 注册输入事件
        event.Op(gtx.Ops, s)
        // 设置光标为列调整光标
        pointer.CursorColResize.Add(gtx.Ops)

        // 循环处理输入事件
        for {
            // 获取事件
            ev, ok := gtx.Event(pointer.Filter{
                Target: s,
                Kinds:  pointer.Press | pointer.Drag | pointer.Release | pointer.Cancel,
            })
            if !ok {
                break
            }

            // 断言事件类型为 pointer.Event
            e, ok := ev.(pointer.Event)
            if !ok {
                continue
            }

            switch e.Kind {
            case pointer.Press:
                // 如果已经在拖动，忽略新的按下事件
                if s.drag {
                    break
                }
                // 记录按下事件的指针ID和位置
                s.dragID = e.PointerID
                s.dragX = e.Position.X
                s.drag = true
            case pointer.Drag:
                // 如果指针ID不匹配，忽略拖动事件
                if s.dragID != e.PointerID {
                    break
                }
                // 计算拖动的距离并更新分割比例
                deltaX := e.Position.X - s.dragX
                s.dragX = e.Position.X
                deltaRatio := deltaX * 2 / float32(gtx.Constraints.Max.X)
                s.Ratio += deltaRatio
                // 如果事件优先级低于抓取，执行抓取命令
                if e.Priority < pointer.Grabbed {
                    gtx.Execute(pointer.GrabCmd{Tag: s, ID: s.dragID})
                }
            case pointer.Release, pointer.Cancel:
                // 结束拖动
                s.drag = false
            }
        }
        // 移除矩形区域
        area.Pop()
    }

    { // 布局左侧组件
        gtx := gtx
        gtx.Constraints = layout.Exact(image.Pt(leftsize, gtx.Constraints.Max.Y))
        left(gtx)
    }

    { // 布局右侧组件
        off := op.Offset(image.Pt(rightoffset, 0)).Push(gtx.Ops)
        gtx := gtx
        gtx.Constraints = layout.Exact(image.Pt(rightsize, gtx.Constraints.Max.Y))
        right(gtx)
        off.Pop()
    }

    // 返回整体布局尺寸
    return layout.Dimensions{Size: gtx.Constraints.Max}
}

// split 是 Split 组件的实例
var split Split

// exampleSplit 示例函数展示如何使用 Split 组件
// gtx 帧上下文
func exampleSplit(gtx layout.Context, th *material.Theme) layout.Dimensions {
    return split.Layout(gtx, func(gtx layout.Context) layout.Dimensions {
        return FillWithLabel(gtx, th, "Left", red)
    }, func(gtx layout.Context) layout.Dimensions {
        return FillWithLabel(gtx, th, "Right", blue)
    })
}

```



## 常见错误

### 我的 list.List 无法滚动

**问题**: 你布局了一个列表，然后它就停在那里，不滚动了。

**解释**: 在 Gio 中，许多组件是无状态的——你可以而且应该每次通过 Layout 函数声明它们。列表并不是这样的。它们内部记录它们的滚动位置，这个状态需要在 Layout 调用之间保持。

**解决方案**: 在事件处理循环之外声明你的列表，并在各帧之间重用它。

----

### 系统忽略了对组件的更新

**问题**: 你在组件结构体中定义了一个包含 gioui.org/widget 提供的类型的字段。你更新了子组件的状态，无论是显式的还是隐式的。子组件固执地拒绝反映你的更新。

这与无法滚动的列表问题有关。

**可能的解释**: 你可能遇到了 Go 代码中的一个常见问题，即你在值接收器上定义了一个方法，而不是指针接收器，因此你对组件所做的所有更新仅在该函数内部可见，并在返回时被丢弃。

**解决方案**: 在有状态组件上的 Layout 和 Update 方法应该使用指针接收器。

----

### 自定义组件忽略了大小

**问题**: 你创建了一个漂亮的新组件。你将它布局，比如在一个 Flex Rigid 中。下一个 Rigid 绘制在它的上面。

**解释**: Gio 动态地通过返回的 layout.Dimensions 传递组件的大小。高级组件（如 Labels）返回或传递它们的尺寸，但低级操作（如 paint.PaintOp）不会自动提供它们的尺寸。

**解决方案**: 计算你使用自定义操作绘制的内容的适当尺寸，并在你的 layout.Dimension 中返回。

----

### 依赖项不再编译

**问题**: 你通过 go get -u gioui.org@latest 更新了 Gio 版本，结果编译失败。

**解释**: 在 Go 中，go get -u（-u 部分）对于 v1.0 之前的版本是不安全的，包括 Gio 和一些依赖项如 typesetting。-u 最终会下载所有依赖项的最新小版本，其中不稳定的依赖项可能会有破坏性的更改。

**解决方案**: 仅使用 go get gioui.org@latest 更新 Gio 依赖项。如果你已经陷入了非常混乱的情况，你可以尝试先将 go.mod 恢复到你之前的提交。

如果以上建议没有帮助，那么你可以尝试删除 go.mod 中除了 module ... 和 go ... 行之外的所有行，然后运行 go mod tidy。这将下载最新的直接依赖项。
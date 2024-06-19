package main

import (
    "fmt"
    "os"
    "syscall"
    "unsafe"
)

type (
    short int16
    word  uint16
    dword uint32
    coord struct {
        X short
        Y short
    }
    smallRect struct {
        Left   short
        Top    short
        Right  short
        Bottom short
    }
    consoleScreenBufferInfo struct {
        Size              coord
        CursorPosition    coord
        Attributes        word
        Window            smallRect
        MaximumWindowSize coord
    }
)

var kernel32 = syscall.NewLazyDLL("kernel32.dll")
var procGetConsoleScreenBufferInfo = kernel32.NewProc("GetConsoleScreenBufferInfo")

func getConsoleScreenBufferInfo(handle syscall.Handle) (consoleScreenBufferInfo, error) {
    var csbi consoleScreenBufferInfo
    ret, _, err := procGetConsoleScreenBufferInfo.Call(uintptr(handle), uintptr(unsafe.Pointer(&csbi)))
    if ret == 0 {
        return csbi, err
    }
    return csbi, nil
}

func main() {
    handle := syscall.Handle(os.Stdout.Fd())
    csbi, err := getConsoleScreenBufferInfo(handle)
    if err != nil {
        fmt.Fprintln(os.Stderr, "Error getting console screen buffer info:", err)
        os.Exit(1)
    }

    args := os.Args[1:]
    if len(args) > 0 {
        switch args[0] {
        case "-w":
            fmt.Println(csbi.Size.X)
        case "-h":
            fmt.Println(csbi.Size.Y)
        default:
            fmt.Fprintf(os.Stderr, "Usage: %s [-w | -h]\n", os.Args[0])
            os.Exit(1)
        }
    } else {
        // If no arguments are provided, default to outputting both width and height
        fmt.Printf("Width: %d\nHeight: %d\n", csbi.Size.X, csbi.Size.Y)
    }
}

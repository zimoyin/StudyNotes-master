





```go
// defer…recover 捕获  panic
func test() {
    //用defer + recover 来捕获和处理异常
    defer func() {
       err := recover() //recover内置函数可以捕获到异常
       if err != nil {  //nil是err的零值
          fmt.Println("err=", err)
          //这里可以把信息发送给管理员
          fmt.Println("发送信息给管理员admin@steven.com")
       }
    }() //匿名函数的调用方式一：func(){}()
    num1 := 10
    num2 := 0
    res := num1 / num2 //除0产生异常，会被defer延迟函数捕获
    fmt.Println("res=", res)
}
func main() {
    //测试
    test()
    for {
       fmt.Println("main()...下面的代码。。。。")
       time.Sleep(time.Second)
    }
}
```



```go
// 判断 error 类型
fi, err := os.Stat("test.txt") 
if err != nil {
    switch err.(type) {
    case *os.PathError:
        // do something
    case *os.LinkError:
        // dome something
    case *os.SyscallError:
        // dome something
    case *exec.Error:
        // dome something
    }
} else {
    // ...
}
```



* 封装 error

```go
err1 := errors.New("this is a error")
err2 := fmt.Errorf("this is a error including the first error: %w\n", err1)
```


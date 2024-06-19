@echo off
setlocal

:: 获取当前盘符
set drive=%~d0

:: 去掉末尾的反斜杠，并加上前斜杠
set drive=%drive:~0,2%/

:: 输出结果
echo 进入到当前盘符根目录，输入 exit 退出
cd /d %drive%
%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe -NoExit chcp 65001

endlocal

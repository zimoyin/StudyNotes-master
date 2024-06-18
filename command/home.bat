@echo off
echo 进入当前用户的桌面目录，输入 exit 退出当前目录环境
cd /d C:\Users\%username%\Desktop
%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe -NoExit chcp 65001
@echo off

REM 指定 .venv 的路径
set VENV_DIR=D:\code\python\python_3_11_5\.venv

REM 如果 .venv 目录不存在，给出错误提示并退出
if not exist "%VENV_DIR%" (
    echo Error: The specified .venv directory does not exist.
    exit /b 1
)

REM 激活 .venv 环境
call "%VENV_DIR%\Scripts\activate.bat"

REM 执行 Python 脚本
python %~dp0\qq_auto.py

REM 关闭 .venv 环境
deactivate

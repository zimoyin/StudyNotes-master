@echo off
echo Path：%~dp0
echo [Command List]
set savePaht=%~dp0
:: 遍历目标路径下所有的 bat 文件并输出文件名（不含后缀）
for %%f in ("%savePaht%\*.bat") do (
    echo * %%~nf
)
apt-get update
pkg install vim -y 
pkg install python -y 
# get-you一个流弊的视频下载软件
pip3 install -i https://pypi.tuna.tsinghua.edu.cn/simple you-get
pkg install nodejs -y 
pkg install openssh -y 
pkg install curl -y 
pkg install git -y
ln -s /storage/emulated/0
# 安装Ubuntu系统
pkg install wget openssl-tool proot -y && hash -r && wget https://raw.githubusercontent.com/EXALAB/AnLinux-Resources/master/Scripts/Installer/Ubuntu/ubuntu.sh && bash ubuntu.sh
ln -s ubuntu-fs/root
# 替换终端
sh -c "$(curl -fsSL https://github.com/Cabbagec/termux-ohmyzsh/raw/master/install.sh)"  

## 换国内源

使用 `pkg update` 更新一下的时候发现默认的官方源网速有点慢，在这个喧嚣浮躁的时代，我们难以静下心等待，这个时候就得更换成国内的 `Termux` 清华大学源了，加快软件包下载速度。

### 方法一：自动替换（推荐）

可以使用如下命令自动替换官方源为 TUNA 镜像源

> `pkg update` 卡住的话多按几次回车 不要傻乎乎的等

```bash
sed -i 's@^\(deb.*stable main\)$@#\1\ndeb https://mirrors.tuna.tsinghua.edu.cn/termux/termux-packages-24 stable main@' $PREFIX/etc/apt/sources.list

sed -i 's@^\(deb.*games stable\)$@#\1\ndeb https://mirrors.tuna.tsinghua.edu.cn/termux/game-packages-24 games stable@' $PREFIX/etc/apt/sources.list.d/game.list

sed -i 's@^\(deb.*science stable\)$@#\1\ndeb https://mirrors.tuna.tsinghua.edu.cn/termux/science-packages-24 science stable@' $PREFIX/etc/apt/sources.list.d/science.list

pkg update
```

更换源几秒钟就可以执行完 `pkg update` 了，心里顿时乐开了花。

### 方法二：手动修改

请使用内置或安装在 Termux 里的文本编辑器，例如 `vi` / `vim` / `nano` 等直接编辑源文件，**不要使用 RE 管理器等其他具有 ROOT 权限的外部 APP** 来修改 Termux 的文件

编辑 `$PREFIX/etc/apt/sources.list` 修改为如下内容

```bash
BASH# The termux repository mirror from TUNA:
deb https://mirrors.tuna.tsinghua.edu.cn/termux/termux-packages-24 stable main
```

编辑 `$PREFIX/etc/apt/sources.list.d/science.list` 修改为如下内容

```bash
BASH# The termux repository mirror from TUNA:
deb https://mirrors.tuna.tsinghua.edu.cn/termux/science-packages-24 science stable
```

编辑 `$PREFIX/etc/apt/sources.list.d/game.list` 修改为如下内容

```bash
BASH# The termux repository mirror from TUNA:
deb https://mirrors.tuna.tsinghua.edu.cn/termux/game-packages-24 games stable
```

**安装基础工具**

更换源之后来赶紧来下载安装一些基本工具吧，这些工具基本上是 Linux 系统自带的，因为 Termux 为了体积不过大，默认是没有带这些工具的，执行下面的命令来安装:

```bash
pkg update
pkg install vim curl wget git tree openssh -y
```

## 终端配色方案

**脚本项目地址**：https://github.com/Cabbagec/termux-ohmyzsh/

该脚本主要使用了 `zsh` 来替代 `bash` 作为默认 shell，并且支持色彩和字体样式，同时也激活了外置存储，可以直接访问 SD 卡下的目录。主题默认为 agnoster，颜色样式默认为 Tango，字体默认为 Ubuntu。

> 执行下面这个命令确保已经安装好了 curl 命令

```bash
sh -c "$(curl -fsSL https://github.com/Cabbagec/termux-ohmyzsh/raw/master/install.sh)"  
```

如果因为不可抗力的原因，出现 `port 443: Connection refused` 网络超时的情况，那么执行下面国光迁移到国内的地址的命令即可：

```bash
sh -c "$(curl -fsSL https://html.sqlsec.com/termux-install.sh)"  
```

Android6.0 以上会弹框确认是否授权访问文件，点击`始终允许`授权后 Termux 可以方便的访问 SD 卡文件。

[![img](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/1587207468173.png)](https://image.3001.net/images/20200418/1587207468173.png)

手机 App 默认只能访问自己的数据，如果要访问手机的存储，需要请求权限，如果你刚刚不小心点了拒绝的话，那么可以执行以下命令来重新获取访问权限：

```bash
termux-setup-storage
```

脚本允许后先后有如下两个选项:

```bash
BASHEnter a number, leave blank to not to change: 14
Enter a number, leave blank to not to change: 6
```

分别选择`色彩样式`和`字体样式`，重启 Termux app 后生效配置。不满意刚刚的效果，想要继续更改配色方案的话，可以根据下面命令来更改对应的色彩配色方案：

**设置色彩样式**：

输入 `chcolor` 命令更换色彩样式，或者执行 `~/.termux/colors.sh` 命令

**设置字体**

运行 `chfont` 命令更换字体，或者执行 `~/.termux/fonts.sh` 命令

## 创建目录软连接

执行过上面的一键配置脚本后，并且授予 Termux 文件访问权限的话，会在家目录生成 `storage` 目录，并且生成若干目录，软连接都指向外置存储卡的相应目录：

[![img](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/15872083523807.png)](https://image.3001.net/images/20200418/15872083523807.png)

**创建 QQ 文件夹软连接**

手机上一般经常使用手机 QQ 来接收文件，这里为了方便文件传输，直接在 `storage` 目录下创建软链接.
**QQ**

```
ln -s /data/data/com.termux/files/home/storage/shared/tencent/QQfile_recv QQ
```

**TIM**

```
ln -s /data/data/com.termux/files/home/storage/shared/tencent/TIMfile_recv TIM
```

[![img](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/15872085834532.png)](https://image.3001.net/images/20200418/15872085834532.png)

这样可以直接在 `home` 目录下去访问 QQ 文件夹，大大提升了文件操作的工作效率。





## 备份与恢复

评论区有网友提问 Termux 有办法打个镜像或者快照吗？😂 怕折腾坏了。也有网友分享了官方 WiKi 已经更新了备份和恢复的方法了，原文是：https://wiki.termux.com/wiki/Backing_up_Termux

下面国光简单搬运过来：

强烈建议在复制粘贴之前了解对应命令的作用。误操作可能会不可挽回地损坏您的数据，数据无价，谨慎操作。

### 备份

确保已经获取了存储访问的权限，如果没有获取的话，执行以下命令来重新获取访问权限：

```
termux-setup-storage
```

然后去 Termux 根目录下：

```
cd /data/data/com.termux/files
```

备份配置文件为 termux-backup.tar.gz：

```
tar -zcf /sdcard/termux-backup.tar.gz home usr
```

备份应该完成，没有任何错误。除非用户滥用 root 权限，否则不应有任何权限拒绝。

> **警告**：不要将备份文件存储在 Termux 私有目录中，因为从设置中清除 Termux 数据后，这些目录也将被删除。（类似于 Windows 准备重新安装系统，却把资料备份存储在 C 盘一个道理）

这些私有目录看上去类似如下的目录：

```
BASH/data/data/com.termux 
/sdcard/Android/data/com.termux
/storage/XXXX-XXXX/Android/data/com.termux
${HOME}/storage/external-1
```

珍爱数据，远离私有目录。

### 恢复

这里假设您已将 Termu 之前备份的 home 和 usr 目录备份到同一个备份文件中。请注意，在此过程中所有文件都将被覆盖现有的配置：

确保已经获取了存储访问的权限，如果没有获取的话，执行以下命令来重新获取访问权限：

```
termux-setup-storage
```

然后去 Termux 根目录下：

```
cd /data/data/com.termux/files
```

解压提取之前备份的内容，覆盖现存的文件并删除之前的备份文件：

```
tar -zxf /sdcard/termux-backup.tar.gz --recursive-unlink --preserve-permissions
```

操作完成重启 Termux 即可恢复数据。



## 主题

我在设置主题的时候有过太多坎坷了



第一个使用方案：先使用了 

```
sh -c "$(curl -fsSL https://html.sqlsec.com/termux-install.sh)" 
```

然后使用了

```
sh -c "$(curl -fsSL https://github.com/Cabbagec/termux-ohmyzsh/raw/master/install.sh)"  
```

最终他崩溃了![image-20220815235350691](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/image-20220815235350691.png)



随后使用方案三

> 2.安装依赖
>
> pkg install zsh
> pkg install git
> 3.将zsh主题设为默认
>
> chsh -s zsh
> 4.配置主题
>
> bash -c "$(curl -fsSL https://gitee.com/lxyoucan/tools/raw/master/common/ohmyzsh_itkey.sh)"

![image-20220815235338870](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/image-20220815235338870.png)

但是 终端还是崩溃的

然后尝试使用`vim .zshrc`来切换终端皮肤，但是没用



![image-20220815235133558](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/image-20220815235133558.png)

最终在 `vim .zshrc.oh-my-zsh ` 才正常切换了终端皮肤



![image-20220815235240573](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/image-20220815235240573.png)



#### 引用：终端配色方案

**脚本项目地址**：https://github.com/Cabbagec/termux-ohmyzsh/

该脚本主要使用了 `zsh` 来替代 `bash` 作为默认 shell，并且支持色彩和字体样式，同时也激活了外置存储，可以直接访问 SD 卡下的目录。主题默认为 agnoster，颜色样式默认为 Tango，字体默认为 Ubuntu。

> 执行下面这个命令确保已经安装好了 curl 命令

```
sh -c "$(curl -fsSL https://github.com/Cabbagec/termux-ohmyzsh/raw/master/install.sh)"  
```

如果因为不可抗力的原因，出现 `port 443: Connection refused` 网络超时的情况，那么执行下面国光迁移到国内的地址的命令即可：

```
sh -c "$(curl -fsSL https://html.sqlsec.com/termux-install.sh)"  
```

Android6.0 以上会弹框确认是否授权访问文件，点击`始终允许`授权后 Termux 可以方便的访问 SD 卡文件。

[![img](termux%E5%88%9D%E5%A7%8B%E5%8C%96.assets/1587207468173-166057852190512.png)](https://image.3001.net/images/20200418/1587207468173.png)

手机 App 默认只能访问自己的数据，如果要访问手机的存储，需要请求权限，如果你刚刚不小心点了拒绝的话，那么可以执行以下命令来重新获取访问权限：

```
termux-setup-storage
```

脚本允许后先后有如下两个选项:

```
BASHEnter a number, leave blank to not to change: 14
Enter a number, leave blank to not to change: 6
```

分别选择`色彩样式`和`字体样式`，重启 Termux app 后生效配置。不满意刚刚的效果，想要继续更改配色方案的话，可以根据下面命令来更改对应的色彩配色方案：

**设置色彩样式**：

输入 `chcolor` 命令更换色彩样式，或者执行 `~/.termux/colors.sh` 命令

**设置字体**

运行 `chfont` 命令更换字体，或者执行 `~/.termux/fonts.sh` 命令
### 如何在Linux 设置代理让其他软件使用

1. 设置代理

```
export https_proxy=http://127.0.0.1:7890 http_proxy=http://127.0.0.1:7890 all_proxy=socks5://127.0.0.1:7891
# 取消代理 
unset all_proxy
unset http_proxy
unset https_proxy
```

1. 设置DNS
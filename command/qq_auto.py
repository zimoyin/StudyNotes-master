from pyzbar.pyzbar import decode
import requests
import win32clipboard
from io import BytesIO
from PIL import Image


def get_image_from_clipboard():
    # 打开剪切板
    win32clipboard.OpenClipboard()
    # 检查剪切板中是否有图片
    if win32clipboard.IsClipboardFormatAvailable(win32clipboard.CF_DIB):
        # 从剪切板中获取图像数据
        data = win32clipboard.GetClipboardData(win32clipboard.CF_DIB)
        # 关闭剪切板
        win32clipboard.CloseClipboard()
        # 使用Pillow库加载图像
        img = Image.open(BytesIO(data))
        return img
    else:
        print("剪切板中没有图片数据。")
        win32clipboard.CloseClipboard()
        return None


def scan_qr_code(image):
    # 使用pyzbar库扫描二维码
    decoded_objects = decode(image)
    if decoded_objects:
        # 返回扫描到的第一个二维码信息
        return decoded_objects[0].data.decode('utf-8')
    else:
        return None


def send_post_request(url):
    # 发送 POST 请求
    response = requests.post("https://qq.b9.pw/index.php?p=api", data={
        "url": url,
        "id": "10",
        "vip": "-ziao",
        "p": "api"
    })
    if response.status_code == 200:
        print(response.text)
    else:
        print(f"POST请求失败，状态码：{response.status_code}")



def main():
    # 读取剪贴板中的图片
    image = get_image_from_clipboard()
    if image is not None:
        # 扫描二维码
        url = scan_qr_code(image)
        print("URL: ",url)
        if url:
            # 发起POST请求
            send_post_request(url)
        else:
            print("未扫描到二维码")
    else:
        print("剪贴板中没有图片数据,请右键二维码图片后复制图片")

# 用于对 优优子 QQ 机器人进行授权
if __name__ == "__main__":
    main()

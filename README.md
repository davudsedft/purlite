# purlite
نسخه سبک پورکاو تحت نام پورلایت.مناسب خانواده ها و کسانی که میخان درگیر تنظیمات اضافه نشن.
این نسخه فقط دارای یک دکمه خاموش و روشن کردن هست.همچنین  خاموش  و روشن  کردن توسط دکمه نوار کشویی بالا و ویجت هم میسر هست.

![purcowin](https://purnet.ir/vpn/lite1.png)
![purcowin](https://purnet.ir/vpn/lite2.png)




برای خروجی گرفتن از کتابخانه گو
go v 1.22

cd v2ray/libs

go mod tidy

go install golang.org/x/mobile/cmd/gomobile@latest

go install golang.org/x/mobile/cmd/gobind@latest

go get golang.org/x/mobile/cmd/gobind

go get golang.org/x/mobile/cmd/gomobile

go get golang.org/x/mobile

gomobile init

gomobile bind -ldflags '-s -w' -o purlite.aar -androidapi 21 -target android .



## Credits
- https://github.com/xtls/xray-core
- https://github.com/2dust/AndroidLibXrayLite
- https://github.com/dev7dev/AndroidLibXrayLite
- https://github.com/gvcgo/vpnparser



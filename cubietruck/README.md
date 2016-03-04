Sunxi-tools instalation

Install required packages

apt-get install libusb-1.0-0-dev pkg-config

download the sinxi-tools source and compile it

git clone git://github.com/linux-sunxi/sunxi-tools/
cd sunxi-tools
make

Configuration

Backup the script.bin

cp /boot/script.bin /boot/script.bin.bck
Convert the script.bin into .fex file (text config file)

./bin2fex /boot/script.bin /boot/script.fex
Open the script.fex with some text editor

nano /boot/script.fex

// TODO прописать настройки

Convert the edited script.fex back into .bin file

./fex2bin /boot/script.fex /boot/script.bin
Turn of the cubieboard

shutdown Цh now


Настройка:
http://docs.cubieboard.org/tutorials/common/gpio_on_lubuntu
http://docs.cubieboard.org/tutorials/cubietruck/start

Описание:
http://vermus.blogspot.ru/2013/12/1-wire-ds9490r-cubietruck-1-wire-ds2401.html
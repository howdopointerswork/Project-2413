#!/bin/bash

OS=$(uname -s)

check(){

	command -v "$1" &> /dev/null

}
	

if check mysql; then
	echo "MySQL is already installed"
	mysql --version
else
	case "$OS"  in
		Darwin)
			echo "Detected macOS"
			if check brew; then
				echo "Installing MySQL with brew..."
				brew install mysql
			else
				#check for curl here
				echo "Brew not found. Installing..."
				/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
				echo "Installing MySQL with brew..."
                        	brew install mysql
			fi
			;;

		Linux)
			echo "Detected Linux"
			echo "Installing MySQL..."
			sudo apt install mysql
			;;

		Windows_NT)
			echo "Detected Windows"
			winget install mysql
			;;
		
		*)
			echo "Unsupported OS"
			;;			
			
	

	esac
fi


if [ $? -eq 0 ]; then
	echo "Sucessfully installed MySQL"
else
	echo "Failed to install MySQL"
#colours?

fi

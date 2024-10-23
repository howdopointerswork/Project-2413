#!/bin/bash

GREEN="\033[32m"

echo "Switching directory.."
sleep 2
cd ../

echo "Getting latest repository..."
sleep 2
git fetch origin

arr=("Backend" "GUI" "database" "main" "misc")

echo "Merging all branches..."

for (( i=0; i<${#arr}; i++ )); do
	echo "Processing branch: ${arr[$i]}"
	git checkout "${arr[$i]}"
	git merge main

done

echo "Switching back to main..."
sleep 2
git checkout main

echo -e "${GREEN}Done${RESET}"

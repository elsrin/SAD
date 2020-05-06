#!/bin/bash
#
# bashChat.sh
#
# View model and end point user interface
#

#UTILS
set -o pipefail
set -o errexit
set -o nounset
#set -o xtrace

LANG=en_US.UTF-8

#COLORS
Black='\0033[0;30m'
DarkGray='\0033[1;30m'
LightGray='\0033[0;37m'
White='\0033[1;37m'
Red='\0033[0;31m'
LightRed='\0033[1;31m'
Green='\0033[0;32m'
LightGreen='\0033[1;32m'
Orange='\0033[0;33m'
Yellow='\0033[1;33m'
Blue='\0033[0;34m'
LightBlue='\0033[1;34m'
Purple='\0033[0;35m'
LightPurple='\0033[1;35m'
Cyan='\0033[0;36m'
LightCyan='\0033[1;36m'
NC='\0033[0m'

#VISUAL UI UTILS
export PS1="" #Eliminates path, user, and machine name from terminal view
clear

#CHAT
state=login #Possible state: login, signUp, chatRoom, error
actualGuest=null

movement(){
    stty -echo
    read -n 1 option
    while [[ "$option" != "w" && "$option" != "a"  && "$option" != "s" && "$option" != "d" && "$option" != "e" ]]; do
	read -n 1 option
    done
    stty echo
}

#SIGN UP VIEW

signUpView(){
    #TERMINAL
    width=$(($(tput cols)-1))
    height=$(tput lines)
  
    #MENU
    subMenuFormWidth=41
    subMenuMargin=9
    subMenuWidth=$((${subMenuFormWidth}+2*${subMenuMargin}+4))
    margin=$(((${width}-${subMenuWidth})/2))
    
    i=1

    firstLine="┌"
    lastLine="└"
    voidLine="│"

    firstSubMenuLine="│"
    lastSubMenuLine="│"
    voidSubMenuLine="│"

    guideLine="│"
    userLine="│"
    passwordLine="│"
    logInLine="│"
    firstFormLine="│"
    lastFormLine="│"
    voidFormLine="│"

    firstFormLineSelected="│"
    lastFormLineSelected="│"
    voidFormLineSelected="│"
    
    while [[ $i -le $width ]]; do
	if [[ $i -ne 0 && $i -ne $width ]]; then
	    firstLine="${firstLine}─"
	    lastLine="${lastLine}─"
	    voidLine="${voidLine} "
	    if [[ $i -eq $margin ]]; then
		firstSubMenuLine="${firstSubMenuLine}┌──────────────────────────────────────────────────────────────┐"
		lastSubMenuLine="${lastSubMenuLine}└──────────────────────────────────────────────────────────────┘"
		voidSubMenuLine="${voidSubMenuLine}│                                                              │"
		guideLine="${guideLine}│    --Please use ${Red}w,a,s,d${NC} to move around and ${Red}e${NC} to select--     │"
		logInLine="${logInLine}│                         -- LOG IN --                         │"
		userLine="${userLine}│          USER                                                │"
		passwordLine="${passwordLine}│          PASSWORD                                            │"
		firstFormLine="${firstFormLine}│         ┌─────────────────────────────────────────┐          │"
		lastFormLine="${lastFormLine}│         └─────────────────────────────────────────┘          │"
		voidFormLine="${voidFormLine}│         │                                         │          │"
		firstFormLineSelected="${firstFormLineSelected}│         ${Red}┌─────────────────────────────────────────┐${NC}          │"
		lastFormLineSelected="${lastFormLineSelected}│         ${Red}└─────────────────────────────────────────┘${NC}          │"
		voidFormLineSelected="${voidFormLineSelected}│         ${Red}│                                         │${NC}          │"
	    elif [[ $i -gt $((${margin}+${subMenuWidth})) || $i -lt ${margin} ]]; then
		firstSubMenuLine="${firstSubMenuLine} "
		lastSubMenuLine="${lastSubMenuLine} "
		voidSubMenuLine="${voidSubMenuLine} "
		guideLine="${guideLine} "
		userLine="${userLine} "
		passwordLine="${passwordLine} "
		logInLine="${logInLine} "
		
		firstFormLine="${firstFormLine} "
		lastFormLine="${lastFormLine} "
		voidFormLine="${voidFormLine} "
		firstFormLineSelected="${firstFormLineSelected} "
		lastFormLineSelected="${lastFormLineSelected} "
		voidFormLineSelected="${voidFormLineSelected} "
		
	    fi
	fi
	((i++))
    done
    firstLine="${firstLine}┐"
    lastLine="${lastLine}┘"
    voidLine="${voidLine}│"

    firstSubMenuLine="${firstSubMenuLine}│"
    lastSubMenuLine="${lastSubMenuLine}│"
    voidSubMenuLine="${voidSubMenuLine}│"
       
    guideLine="${guideLine}│"
    userLine="${userLine}│"
    passwordLine="${passwordLine}│"
    logInLine="${logInLine}│"
    firstFormLine="${firstFormLine}│"
    lastFormLine="${lastFormLine}│"
    voidFormLine="${voidFormLine}│"
    
    firstFormLineSelected="${firstFormLineSelected}│"
    lastFormLineSelected="${lastFormLineSelected}│"
    voidFormLineSelected="${voidFormLineSelected}│"
    
    heightMargin=$((($height-16)/2))
    
    i=2
    echo -e "${firstLine}"
    while [[ $i -lt $height ]]; do
	if [[ $i -lt $heightMargin || $i -gt $((${heightMargin}+11)) ]]; then
	   echo -e "${voidLine}"
	elif [[ $i -eq $heightMargin ]]; then
	    echo -e "${firstSubMenuLine}"
	    ((i++))
	    echo -e "${voidSubMenuLine}"
	    ((i++))
	    echo -e "${logInLine}"
	    ((i++))
	    echo -e "${voidSubMenuLine}"
	    ((i++))
	    echo -e "${userLine}"
	    ((i++))
	    echo -e "${firstFormLineSelected}"
	    ((i++))
	    echo -e "${voidFormLineSelected}"
	    userForm=$i
	    ((i++))
	    echo -e "${lastFormLineSelected}"
	    ((i++))
	    echo -e "${voidSubMenuLine}"
	    ((i++))
	    echo -e "${passwordLine}"
	    ((i++))
	    echo -e "${firstFormLine}"
	    ((i++))
	    echo -e "${voidFormLine}"
	    passwordForm=$i
	    ((i++))
	    echo -e "${lastFormLine}"
	    ((i++))
	    echo -e "${voidSubMenuLine}"
	    ((i++))
	    echo -e "${guideLine}"
	    ((i++))
	    echo -e "${voidSubMenuLine}"
	    ((i++))
	    echo -e "${lastSubMenuLine}"
	fi
	((i++))
    done
    echo -e "${lastLine}"

    microstate="user" #user,password,enter
    readFormLine=$(echo ${voidFormLineSelected} | sed "s/\(│.*│.*│\)/\1/")
    
    logInResponse="dennied"

    while [[ ! "$logInResponse" == "approved" ]]; do
	movement
	case $option in
	    w) if [[ "$microstate" == "password" ]]; then
		   microstate=user
		   echo -e "\e[${userForm}A${voidFormLineSelected}"
		   echo -e "${lastFormLineSelected}"
		   echo -e "\e[2A${firstFormLineSelected}"
		   
		   
	       fi
	       ;;
	    s) if [[ "$microstate" == "user" ]]; then
		   microstate=password
		   echo -e "\e[${passwordForm}A${voidFormLineSelected}"
		   echo -e "${lastFormLineSelected}"
		   echo -e "\e[2A${firstFormLineSelected}"
		   
	       fi
	       ;;
	    e) if [[ "$microstate" == "user" ]]; then
		   read -n 25 -p "$(echo -e ${readFormLine})" userattempt
	       elif [[ "$microstate" -eq "password" ]]; then
		   read -n 25 -p "$(echo -e ${readFormLine})" passattempt
	       fi
	       ;;
	esac   
    done
}

signUpView



#!/bin/bash
#
# bashChat.sh
#
# View model and end point user interface
#

#UTILS
set -o pipefail
#set -o errexit
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

#CHAT
state=login #Possible state: login, signUp, chatRoom, error
actualGuest=null

#SIGN UP VIEW
signUpView(){

    #TERMINAL
    width=$(tput cols)
    height=$(tput lines)
  
    #MENU
    subMenuWidth=30
    margin=$(((${width}-${subMenuWidth})/2))
    
    i=1
    firstLine="┌"
    lastLine="└"
    voidLine="│"
    firstSubMenuLine="│"
    lastSubMenuLine="│"
    voidSubMenuLine="│"
    while [[ $i -le $width ]]; do
	if [[ $i -ne 0 && $i -ne $width ]]; then
	    firstLine="${firstLine}─"
	    lastLine="${lastLine}─"
	    voidLine="${voidLine} "
	    if [[ $i -gt $margin && $i -lt $((${margin}+${subMenuWidth})) ]]; then
		
		
	    
	    else



	    fi
	fi
    done
    firstLine="${firstline}┐"
    lastLine="${lastline}┘"
    voidLine="${voidline}│"

}



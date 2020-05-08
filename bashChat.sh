#!/bin/bash
#
# bashChat.sh
#
# View model and end point user interface
#

#UTILS
set -o pipefail
#set -o errexit
#set -o nounset
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
server_history="/tmp/bash_chat"

movement(){
    stty -echo
    echo -e "\e[1A"
    read -n 1 -t 1 option
    while [[ "$option" != "w" && "$option" != "a"  && "$option" != "s" && "$option" != "d" && "$option" != "e" ]]; do
	echo -e "\e[1A"
	read -n 1 -t 1 option
	if [[ $state == "chatRoom" ]]; then
	    refreshChat
	fi
    done
    stty echo
}

#
# SIGN UP VIEW ----------------------------------------------------------------------------
#

signUpView(){
    #TERMINAL
    width=$(($(tput cols)-1))
    height=$(($(tput lines)-1))
    color="White"
    user="Loser"
    
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
    colorLine="│"
    logInLine="│"
    firstFormLine="│"
    lastFormLine="│"
    voidFormLine="│"
    buttonLine="│"
    buttonLineSelected="│"
    
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
		passwordLine="${passwordLine}│          COLOR                                               │"
		colorLine="${colorLine}│          -- ${Red}Red ${Blue}Blue ${Green}Green ${Yellow}Yellow ${Purple}Purple ${Orange}Orange${NC} --           │"
		firstFormLine="${firstFormLine}│         ┌─────────────────────────────────────────┐          │"
		lastFormLine="${lastFormLine}│         └─────────────────────────────────────────┘          │"
		voidFormLine="${voidFormLine}│         │                                         │          │"
		buttonLine="${buttonLine}│         │                  ENTER                  │          │"
		firstFormLineSelected="${firstFormLineSelected}│         ${Red}┌─────────────────────────────────────────┐${NC}          │"
		lastFormLineSelected="${lastFormLineSelected}│         ${Red}└─────────────────────────────────────────┘${NC}          │"
		voidFormLineSelected="${voidFormLineSelected}│         ${Red}│                                         │${NC}          │"
		buttonLineSelected="${buttonLineSelected}│         ${Red}│                  ENTER                  │${NC}          │"
	    elif [[ $i -gt $((${margin}+${subMenuWidth})) || $i -lt ${margin} ]]; then
		firstSubMenuLine="${firstSubMenuLine} "
		lastSubMenuLine="${lastSubMenuLine} "
		voidSubMenuLine="${voidSubMenuLine} "
		guideLine="${guideLine} "
		userLine="${userLine} "
		passwordLine="${passwordLine} "
		colorLine="${colorLine} "
		logInLine="${logInLine} "
		buttonLine="${buttonLine} "
		buttonLineSelected="${buttonLineSelected} "
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
    colorLine="${colorLine}│"
    logInLine="${logInLine}│"
    firstFormLine="${firstFormLine}│"
    lastFormLine="${lastFormLine}│"
    voidFormLine="${voidFormLine}│"
    buttonLine="${buttonLine}│"
    buttonLineSelected="${buttonLineSelected}│"
    
    
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
    	    userForm=$(($height-$i))
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
	    passwordForm=$(($height-$i))
	    ((i++))
	    echo -e "${lastFormLine}"
	    ((i++))
	    echo -e "${colorLine}"
	    ((i++))
	    echo -e "${voidSubMenuLine}"
	    ((i++))
	    echo -e "${firstFormLine}"
	    ((i++))
	    echo -e "${buttonLine}"
	    buttonForm=$(($height-$i))
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

    while [[ true ]]; do
	movement
	case $option in
	    w) if [[ "$microstate" == "password" ]]; then
		   microstate=user
		   echo -e "\e[$((${passwordForm}+1))A${voidFormLine}"
		   echo -e "${lastFormLine}"
		   echo -e "\e[3A${firstFormLine}"
		   echo -e "\e[$((${passwordForm}))B"

		   echo -e "\e[$((${userForm}+1))A${voidFormLineSelected}"
		   echo -e "${lastFormLineSelected}"
		   echo -e "\e[3A${firstFormLineSelected}"
		   echo -e "\e[$((${userForm}))B"
	       elif [[ "$microstate" == "enter" ]]; then
		   microstate=password
		   echo -e "\e[$((${passwordForm}+1))A${voidFormLineSelected}"
		   echo -e "${lastFormLineSelected}"
		   echo -e "\e[3A${firstFormLineSelected}"
		   echo -e "\e[$((${passwordForm}))B"

		   echo -e "\e[$((${buttonForm}+1))A${buttonLine}"
		   echo -e "${lastFormLine}"
		   echo -e "\e[3A${firstFormLine}"
		   echo -e "\e[$((${buttonForm}))B"
		   
	       fi
	       ;;
	    s) if [[ "$microstate" == "user" ]]; then
		   microstate=password
		   echo -e "\e[$((${passwordForm}+1))A${voidFormLineSelected}"
		   echo -e "${lastFormLineSelected}"
		   echo -e "\e[3A${firstFormLineSelected}"
		   echo -e "\e[$((${passwordForm}))B"

		   echo -e "\e[$((${userForm}+1))A${voidFormLine}"
		   echo -e "${lastFormLine}"
		   echo -e "\e[3A${firstFormLine}"
		   echo -e "\e[$((${userForm}))B"
	       elif [[ "$microstate" == "password" ]];then
		   microstate=enter
		   echo -e "\e[$((${passwordForm}+1))A${voidFormLine}"
		   echo -e "${lastFormLine}"
		   echo -e "\e[3A${firstFormLine}"
		   echo -e "\e[$((${passwordForm}))B"

		   echo -e "\e[$((${buttonForm}+1))A${buttonLineSelected}"
		   echo -e "${lastFormLineSelected}"
		   echo -e "\e[3A${firstFormLineSelected}"
		   echo -e "\e[$((${buttonForm}))B"
		   
	       fi
	       ;;
	    e) if [[ "$microstate" == "user" ]]; then
		   stty -raw
		   echo -e "\e[$((${userForm}+2))A"
		   read -p "$(echo -e "\e[$(($margin+12))C" )" user
		   echo -e "\e[$((${userForm}-1))B"
	       elif [[ "$microstate" == "password" ]]; then
		   stty -raw
		   echo -e "\e[$((${passwordForm}+2))A"
		   read -p "$(echo -e "\e[$(($margin+12))C" )" color
		   echo -e "\e[$((${passwordForm}-1))B"
	       elif [[ "$microstate" == "enter" ]]; then
		   state="chatRoom"
		   ChatRoomView
	       fi
	       ;;
	esac   
    done
}

#
# CHAT ROOM VIEW -----------------------------------------------------------------
#

ChatRoomView(){
    #TERMINAL
    width=$(($(tput cols)-1))
    height=$(($(tput lines)-3))

    #MENU
    i=1

    clear
    firstLine="┌─"
    firstBoxLine="│┌"
    lastBoxLine="│└"    
    lastLine="└─"
    voidLine="││"
    separatorLine="├─"
    firstBoxLineSelected="│${Red}┌"
    lastBoxLineSelected="│${Red}└"
    voidLineSelected="│${Red}│${NC}${message}${Red} "
    readBoxVoidLine="││${message} "
    message=""
    
    while [[ $i -le $width ]]; do
	if [[ $i -gt 2 && $i -lt $(($width-1)) ]]; then
	    firstLine="${firstLine}─"
	    lastLine="${lastLine}─"
	    firstBoxLine="${firstBoxLine}─"
	    lastBoxLine="${lastBoxLine}─"
	    separatorLine="${separatorLine}─"
	    voidLine="${voidLine} "
	    
	    firstBoxLineSelected="${firstBoxLineSelected}─"
	    lastBoxLineSelected="${lastBoxLineSelected}─"
	    if [[ $i -eq $((${width}-10)) ]]; then

		readBoxTopLine="${firstBoxLine}┐┌──────┐│"
		readBoxVoidLine="${voidLine}││ SEND ││"
		readBoxLastLine="${lastBoxLine}┘└──────┘│"

		readBoxFirstLineSelected="${firstBoxLineSelected}┐${NC}┌──────┐│"
		readBoxVoidLineSelected="${voidLineSelected}│${NC}│ SEND ││"
		readBoxLastLineSelected="${lastBoxLineSelected}┘${NC}└──────┘│"
		
		sendFirstSelected="${firstBoxLine}┐${Red}┌──────┐${NC}│"
		sendVoidSelected="${voidLine}│${Red}│ SEND │${NC}│"
		sendLastSelected="${lastBoxLine}┘${Red}└──────┘${NC}│"
		
	    fi
	    if [[ $i -lt $((${width}-${#message})) ]];then
		voidLineSelected="${voidLineSelected} "
		readBoxVoidLine="${readBoxVoidLine} "
	    fi
	fi
	((i++))
    done
    firstLine="${firstLine}─┐"
    lastLine="${lastLine}─┘"
    voidLine="${voidLine}││"
    separatorLine="${separatorLine}─┤"
    firstBoxLine="${firstBoxLine}┐│"
    lastBoxLine="${lastBoxLine}┘│"    
        
    
    i=2
    echo -e "${firstLine}"
    echo -e "${firstBoxLine}"
    while [[ $i -lt $(($height-4)) ]]; do
	echo -e "${voidLine}"
	((i++))
    done
    echo -e "${lastBoxLine}"
    echo -e "${separatorLine}"
    echo -e "${readBoxFirstLineSelected}"
    echo -e "${readBoxVoidLineSelected}"
    echo -e "${readBoxLastLineSelected}"
    echo -e "${lastLine}"
    
    microstate="write" #write,enter
  
    while [[ true ]]; do
	refreshChat
	movement
	refreshChat

	voidLine="││${message}"
	voidLineSelected="│${Red}│${NC}${message}${Red}"
	i=2
	while [[ $i -lt $((${width}-${#message}-10)) ]]; do
            voidLine="${voidLine} "
	    voidLineSelected="${voidLineSelected} "
	    ((i++))
	done
	readBoxVoidLineSelected="${voidLineSelected}│${NC}│ SEND ││"
	sendVoidSelected="${voidLine}│${Red}│ SEND │${NC}│"

	
	case $option in
	    a) if [[ "$microstate" == "enter" ]]; then
		   microstate=write
		   echo -e "\e[${height}H${readBoxVoidLineSelected}"
		   echo -e "${readBoxLastLineSelected}"
		   echo -e "\e[3A${readBoxFirstLineSelected}"
		   echo -e "\e[$((${height}+2))H" 
	       fi
	       ;;
	    d) if [[ "$microstate" == "write" ]]; then
		   microstate=enter
		   echo -e "\e[${height}H${sendVoidSelected}"
		   echo -e "${sendLastSelected}"
		   echo -e "\e[3A${sendFirstSelected}"
		   echo -e "\e[$((${height}+2))H" 
	       fi
	       ;;
	    e) if [[ "$microstate" == "write" ]]; then
		   message=""
		   echo -e "\e[4A"
		   read -n $((${width}-14)) -p "$(echo -e "\e[2C" )" message
		   echo -e "\e[$1B"
	       elif [[ "$microstate" == "enter" && -n $message ]]; then
		   eval c='$'${color}
		   echo -e "${c}${user}${NC} > ${message}" >> $server_history
		   message=""
	       fi
	       ;;
	esac   
    done
}

refreshChat(){
    width=$(($(tput cols)-1))
    height=$(($(tput lines)-3))

    chatInputs=$((${height}-7))
    clearLine="││"
    i=2
    while [[ $i -lt $width ]]; do
	clearLine="${clearLine} "
	((i++))
    done
    clearLine="${clearLine}││"
    i=3
    while read l; do
	echo -e "\e[${i}H${clearLine}"
	echo -e "\e[${i}H\e[3C${l}"
	((i++))
    done < <(cat $server_history | tail -${chatInputs})
    echo -e "\e[$((${height}+2))H" 
}

signUpView



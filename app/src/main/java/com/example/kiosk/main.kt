package com.example.kiosk

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread

fun main(){
    val menu=ArrayList<Menu>()
    init(menu)

    var money:Int?
    val total= arrayOf<Int>(0)


    //결제시 주문 대기수 올라감
    var waiting=0



    while (true){
        println("현재 소지한 금액을 입력해주십시오")
        try {
            money= readLine()?.toString()?.toInt()
            if(money==null||money<0){
                println("다시 입력해주세요")
                continue
            }
        }catch (e:Exception){
            println("다시 입력해주세요")
            continue
        }
        break
    }

    //주문 대기수
    //결제시 주문대기자 수 증가
    var endFlag=true
    val wait=thread(start=true){
        try {
            Thread.sleep(5000)
        }catch (e:Exception){
        }
        println("현재 주문 대기수:${waiting}")
        while (endFlag){
            try {
                Thread.sleep(5000)
            }catch (e:Exception){
            }
        }
        println("종료되었습니다")
    }

    while (true){

        println("\n로딩중...")
        try {
            Thread.sleep(3000)
        }catch (e:Exception){}

        println()

        var category:Int?
        try {
            println("\n[MENU]\n")
            println("1.피자\n2.음료\n3.사이드\n4.주문확인 및 수정\n5.결제\n0.종료")
            category= readLine()?.toString()?.toInt()
            if(category!=null&&category==0) break

            if(category==null||(category!=0&&category !in 1..5)) {
                println("다시 입력해주세요")
                continue
            }

        }catch (e: Exception){
            println("다시 입력해주세요")
            continue
        }

        if(category==5){
            if(total[0]==0){
                println("아직 주문하지 않았습니다. 메뉴를 담은 후에 결제해주세요")
            }else{
                checkOrder(menu,total,false)
                println("결제하시겠습니까?\n1.Yes    2.No")
                var choose:Int?
                while (true){
                    try {
                        choose= readLine()?.toString()?.toInt()
                        if(choose==null||choose !in 1..2){
                            println("다시 입력해주세요")
                            continue
                        }
                    }catch (e: Exception){
                        println("다시 입력해주세요")
                        continue
                    }
                    break

                }
                if(choose==1){
                    val time=LocalDateTime.now()
                    val form=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val formtime=DateTimeFormatter.ofPattern("HHmm")
                    val curtime=time.format(formtime).toInt()

                    if(curtime in 2000..2100){
                        println("현재시간은 ${curtime/100}시 ${curtime%100}분입니다.")
                        println("은행 점검 시간은 20시 00분 ~ 21시:00이므로 결제할 수 없습니다.")
                        continue
                    }


                    if(money!!<total[0]){
                        println("현재 잔액은 ${money}원으로 ${total[0]-money}원이 부족합니다")
                        continue
                    }
                    money = money!!.minus(total[0])
                    total[0]=0
                    menu.clear()
                    init(menu)


                    println("결제가 완료되었습니다(${time.format(form)})")
                    println("남은 금액: ${money}원")
                    ++waiting
                }else{
                    println("결제가 취소되었습니다")
                }


            }

        }else{
            orderMenu(menu,category,total)
        }

    }

    endFlag=false

}

fun init(menu:ArrayList<Menu>){
    menu+=Cola()
    menu+=Cider()
    menu+=Beer()
    menu+=FrenchFries()
    menu+=GrilledChicken()
    menu+=CheesePizza()
    menu+=PepperoniPizza()
    menu+=PotatoPizza()
}

fun orderMenu(menu: ArrayList<Menu>, category: Int, total:Array<Int>){
    if(category==4){
        return chageOrder(menu,total)
    }

    val tmpMenu=ArrayList<Menu>()

    for(i in menu){
        if(category==1){
            if(i is Pizza){
                tmpMenu+=i
            }
        }else if(category==2){
            if(i is Drink){
                tmpMenu+=i
            }
        }else if(category==3){
            if(i is Side){
                tmpMenu+=i
            }
        }
    }

    val title=when(category){
        1->"[Pizza]"
        2->"[Drink]"
        else->"[Side]"
    }

    println("\n${title}\n")
    for(i in tmpMenu.indices) {
        print("${i+1}." )
        tmpMenu[i].displayInfo()
    }
    println("0.뒤로가기")

    var orders:Int?
    while (true){
        try{
            orders= readLine()?.toString()?.toInt()
            if(orders!=null&&orders==0) return
            if(orders==null||orders !in 1..tmpMenu.size){
                println("다시 입력해주세요")
                continue
            }
        }catch (e:Exception){
            println("다시 입력해주세요")
            continue
        }
        break
    }

    total[0]+=tmpMenu[orders!!-1].order()


}

fun chageOrder(menu: ArrayList<Menu>,total: Array<Int>){
    if(!checkOrder(menu,total,true)) return
    println("수정을 원하는 메뉴를 선택해주세요")
    var select:Int?

    var size=menu.filter{it.flag==true}.size
    while (true){
        try {
            select= readLine()?.toString()?.toInt()
            if(select!=null&&select==0) return
            if(select==null||select !in -1..size){
                println("다시 입력해주세요")
                continue
            }
        }catch (e:Exception){
            println("다시 입력해주세요")
            continue
        }
        break
    }

    if(select==-1){
        menu.clear()
        init(menu)
        total[0]=0
    }else{
        for(i in menu){
            if(i.flag){
                size--
            }
            if(size==0){
                total[0]+=i.changeOrder()
                break
            }
        }
    }
}

fun checkOrder(menu: ArrayList<Menu>,total: Array<Int>,flags:Boolean):Boolean{

    //주문이 하나도 없으면 종료
    var flag=false
    for(i in menu){
        if(i.flag) flag=true
    }
    if(!flag){
        println("아직 담은 메뉴가 없습니다.\n")
        return flag
    }

    println("\n[Order]")
    var idx=1
    for(i in menu.indices){
        if(menu[i].flag) print("${idx++}.")
        menu[i].orderList()
    }
    if(flags){
        println("0.뒤로가기\n-1.전체 삭제")
    }


    println("\n[Total]\n${total[0]} 원")

    return flag
}
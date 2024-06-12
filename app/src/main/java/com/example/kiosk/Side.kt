package com.example.kiosk

abstract class Side:Menu() {
    abstract val name:String
    abstract val price:Int
    var order:Int=0
    override var flag=false //주문 수량이 없으면 false

    override fun displayInfo() {
        println("이름: ${name}, 가격: ${price}")
    }

    override fun orderList() {
        if(!flag) return
        println("이름: ${name}, 주문수: ${order}")
    }

    override fun order():Int {
        var num:Int?
        while(true){
            try {
                println("수량을 입력해 주세요(0.메인메뉴로)")
                num= readLine()?.toString()?.toInt()
                if(num==0) return 0
                if(num==null||num<=0){
                    println("다시 입력해주세요")
                    continue
                }
            }catch (e:Exception){
                println("다시 입력해주세요")
                continue
            }

            println("주문완료: 수량-${num}")
            order+=num
            flag=true
            break
        }
        return num!!*price
    }

    override fun changeOrder():Int {
        var select:Int?
        var num:Int?

        println("수량을 입력해주세요(정수로 입력)")
        while (true){
            try {
                num= readLine()?.toString()?.toInt()
                if(num==null||num+order<0){
                    println("다시 입력해주세요")
                    continue
                }
            }catch (e:Exception){
                println("다시 입력해주세요")
                continue
            }
            break
        }
        order+=num as Int
        if(order==0){
            flag=false
        }

        return num*price

    }
}
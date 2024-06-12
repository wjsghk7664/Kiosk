package com.example.kiosk

abstract class Drink:Menu() {
    abstract val name:String
    abstract val price:Int
    var order=arrayOf(0,0) //얼음유무로. 앞이 얼음있는것 뒤가 얼음 없는것
    override var flag=false //주문 수량이 없으면 false

    override fun displayInfo() {
        println("이름: ${name}, 가격: ${price}")
    }

    override fun orderList() {
        if(!flag) return
        println("이름: ${name}, 주문수: 얼음 있음-${order[0]} / 얼음 없음-${order[1]}")
    }

    override fun order():Int {
        var ice:Int?
        var num:Int?
        while(true){
            try {
                println("얼음을 넣으시겠습니까?\n1.Yes\n2.No\n0.메인메뉴로")
                ice= readLine()?.toString()?.toInt()
                if(ice==0) return 0

                if(ice==null||(ice!=1&&ice!=2)){
                    println("다시 입력해주세요")
                    continue
                }

                println("수량을 입력해 주세요")
                num= readLine()?.toString()?.toInt()
                if(num==null||num<=0){
                    println("다시 입력해주세요")
                    continue
                }
            }catch (e:Exception){
                println("다시 입력해주세요")
                continue
            }

            println("주문완료: 얼음 ${if(ice==1) "넣음" else "안 넣음"} / 수량-${num}")
            flag=true

            when(ice){
                0 ->order[0]+=num
                else ->order[1]+=num
            }
            break
        }
        return num!!*price
    }

    override fun changeOrder():Int {
        var select:Int?
        var num:Int?
        println("수정을 원하는 타입을 입력해주세요\n1.얼음 있음    2.얼음 없음")
        while (true){
            try {
                select= readLine()?.toString()?.toInt()
                if(select==null||select !in 1..2){
                    println("다시 입력해주세요")
                    continue
                }
            }catch (e:Exception){
                println("다시 입력해주세요")
                continue
            }
            break
        }
        println("수량을 입력해주세요(정수로 입력)")
        while (true){
            try {
                num= readLine()?.toString()?.toInt()
                if(num==null||num+order[select!!-1]<0){
                    println("다시 입력해주세요")
                    continue
                }
            }catch (e:Exception){
                println("다시 입력해주세요")
                continue
            }
            break
        }
        order[select!!-1]+=num as Int
        if(order.sum()==0){
            flag=false
        }

        return num*price

    }

}
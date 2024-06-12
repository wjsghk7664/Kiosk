package com.example.kiosk

abstract class Pizza:Menu() {
    abstract val name:String
    abstract val price:Int
    var order=arrayOf(0,0,0) //사이즈 별로 첫번째가 스몰, 두번째가 레귤러, 세번째가 라지
    override var flag=false //주문 수량이 없으면 false

    override fun displayInfo() {
        println("이름: ${name}, 가격: ${price}")
    }

    override fun orderList() {
        if(!flag) return
        println("이름: ${name}, 주문수: 스몰-${order[0]} / 레귤러-${order[1]} / 라지-${order[2]}")
    }

    override fun order():Int {
        var sizesTmp:Int?
        var num:Int?
        while(true){
            try {
                println("\n[Size]\n")
                println("1.스몰\n2.레귤러\n3.라지\n0.메인메뉴로")
                sizesTmp= readLine()?.toString()?.toInt()
                if(sizesTmp==0) return 0

                if(sizesTmp==null||sizesTmp !in 0..3){
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

            val tmpSize=when(sizesTmp){
                1->"스몰"
                2->"레귤러"
                else->"라지"
            }
            println("주문완료: 사이즈-${tmpSize} / 수량-${num}")
            flag=true

            when(sizesTmp){
                1 ->order[0]+=num
                2 ->order[1]+=num
                else ->order[2]+=num
            }
            break
        }
        return num!!*price
    }

    override fun changeOrder():Int {
        var select:Int?
        var num:Int?
        println("수정을 원하는 사이즈를 입력해주세요\n1.스몰    2.레귤러    3.라지")
        while (true){
            try {
                select= readLine()?.toString()?.toInt()
                if(select==null||select !in 1..3){
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
        return num* price


    }


}
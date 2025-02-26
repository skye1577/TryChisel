package fifo

import chisel3._
import chisel3.util.{DecoupledIO, ReadyValidIO, ValidIO, log2Ceil}

//import _root_.circt.stage.ChiselStage

class FIFO[T <: Data] (gen: T,depth: Int) extends Module {
  val io = IO(new Bundle {
    val enq = Flipped(DecoupledIO(gen))
    val deq = DecoupledIO(gen)
  })

//  val queue = RegInit(VecInit(Seq.fill(depth)(0.U.asTypeOf(gen)))) // FIFO队列
  val queue = Reg(Vec(depth, gen))
  val ptr_width = log2Ceil(depth)
  val head = RegInit(0.U(ptr_width.W))
  val tail = RegInit(0.U(ptr_width.W))

  val count = Reg(UInt(log2Ceil(depth + 1).W)) // FIFO中的元素数量

  // 初始化
  when(reset.asBool) {
    head := 0.U
    tail := 0.U
    count := 0.U
    queue := VecInit(Seq.fill(depth)(0.U.asTypeOf(gen))) // 初始化队列
  }
  // FIFO是否满或空
  val isFull = count === depth.U
  val isEmpty = count === 0.U

  //读操作
  when (io.deq.ready && !isEmpty) {
    head := (head + 1.U) % depth.U
    count := count - 1.U
  }

  //写操作
  when (io.enq.valid && !isFull){
    queue(tail) := io.enq.bits;
    tail := (tail + 1.U) % depth.U
    count := count + 1.U
  }

  // 连接输出
  io.enq.ready := !isFull // 入队端准备好，当队列未满时
  io.deq.valid := !isEmpty // 出队端有效，当队列非空时
  io.deq.bits := queue(head) // 输出队列头部的数据

}

object Main extends App {
  emitVerilog(new FIFO(UInt(8.W),depth = 4), args)
  println("FIFO Generated Success! ")




  // 生成 Verilog
  emitVerilog(gen= new FIFO(UInt(8.W), depth = 4), args = Array("--target", "verilog"))// 指定生成 Verilog 代码 )// 指定 Verilog 2005 语法)

  println("FIFO Verilog Generated Success! ")

}

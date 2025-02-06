package alu

import chisel3._
import chisel3.util._
import chisel3.experimental._



class Adder(width: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(width.W))
    val b = Input(UInt(width.W))
    val sum = Output(UInt(width.W))
  })
  io.sum := io.a + io.b
}

object Main extends App {
  println("TEST")
  emitVerilog(new Adder(4), args)
}

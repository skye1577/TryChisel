package node_map
import chisel3._

// 定义一个简单的反相模块
class Inverter extends Module {
  val io = IO(new Bundle {
    val in  = Input(UInt(1.W))  // 输入信号
    val out = Output(UInt(1.W)) // 输出信号
  })

  // 输出信号是输入信号的反相
  io.out := ~io.in
}

// 定义一个简单的缓冲模块
class Buffer extends Module {
  val io = IO(new Bundle {
    val in  = Input(UInt(1.W))
    val out = Output(UInt(1.W))
  })
  io.out := io.in  // 输出等于输入
}

// 定义一个顶层模块，连接 Inverter 和 Buffer
class Connect extends Module {
  val io = IO(new Bundle {
    val input = Input(UInt(1.W))
    val output = Output(UInt(1.W))
  })

  // 实例化子模块
  val inverter = Module(new Inverter())
  val buffer = Module(new Buffer())

  // 连接信号
  inverter.io.in := io.input
  buffer.io.in := inverter.io.out
  io.output := buffer.io.out
}

// 测试顶层模块
object NodeMap extends App {
  emitVerilog(new Connect())
}
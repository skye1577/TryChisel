// Generated by CIRCT firtool-1.62.1
module Adder(	// example/src/main/scala/Adder.scala:9:7
  input        clock,	// example/src/main/scala/Adder.scala:9:7
               reset,	// example/src/main/scala/Adder.scala:9:7
  input  [3:0] io_a,	// example/src/main/scala/Adder.scala:10:14
               io_b,	// example/src/main/scala/Adder.scala:10:14
  output [3:0] io_sum	// example/src/main/scala/Adder.scala:10:14
);

  assign io_sum = io_a + io_b;	// example/src/main/scala/Adder.scala:9:7, :15:18
endmodule


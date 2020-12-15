package com.github.tosametal

class Counter(private var count: Int) {
  def increment(): Unit = count += 1
  def getCount: Int = count
}

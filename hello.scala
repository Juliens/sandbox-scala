import scala.io.Source;

  object Box {
    private var count:Int = 0;
    private var boxes:Array[Box] = Array[Box]();
    def add(x:Int, y:Int, z:Int) = {
      boxes = boxes ++ Array[Box](new Box(x, y, z, count));
      boxes = boxes ++ Array[Box](new Box(x, z, y, count));
      boxes = boxes ++ Array[Box](new Box(z, y, x, count));
      boxes = boxes ++ Array[Box](new Box(z, x, y, count));
      count += 1;
    }

    def dump = {
      //println(boxes mkString "\n");
      /*
      println(boxes(8).getAllPossibleNextIn(boxes) mkString "\n");
      println("");
      println("");
      println(boxes(4).getAllPossibleNextIn(boxes) mkString "\n");
      println("");
      println("");
      println(boxes(0).getAllPossibleNextIn(boxes) mkString "\n");
      */
      val stackes:Array[Array[Box]] = for (stack <- boxes) yield stack.getLongestStackIn(boxes);
      //println(Box.biggestBoxesHeight(stackes) mkString "\n");
      println(Box.getBoxesHeight(Box.biggestBoxesHeight(stackes)));
    }

    def createFromList(list:Array[String]):Unit = {
      if (list.length>0) {
        val coordinates = list.head.split(' ');
        val x = coordinates(0).toInt;
        val y = coordinates(1).toInt;
        val z = coordinates(2).toInt;
        add(x,y,z);
        createFromList(list.tail);
      }
    }
    def getBoxesHeight(boxes:Array[Box]):Int = {
      if (boxes.length==1) {
        boxes.head.z;
      } else {
        boxes.head.z+Box.getBoxesHeight(boxes.tail);
      }
    }

    def biggestBoxesHeight(arBoxes:Array[Array[Box]]):Array[Box] = {
      if (arBoxes.length<=1) {
        arBoxes.head;
      } else {
        val biggestTail = biggestBoxesHeight(arBoxes.tail);
        val headHeight = getBoxesHeight(arBoxes.head);
        val tailHeight = getBoxesHeight(biggestTail);
        if (headHeight>tailHeight) {
          arBoxes.head;
        } else {
          biggestTail;
        }
        
      }
    }

  }

  class Box(px:Int, py:Int, pz:Int, pid:Int) {
    val x:Int = px;
    val y:Int = py;
    val z:Int = pz;
    val id:Int = pid;
    override def toString:String = {
      "["+x+", "+y+", "+z+"]"
    }
    
    def canBeUnder(that:Box):Boolean = {
      ((this.x<=that.x && this.y<=that.y) || (this.x<=that.y && this.y<=that.x))
    }

    def getAllPossibleNextIn(that:Array[Box]):Array[Box] = {
      that filter (this.id != _.id) filter (this canBeUnder _)
    }


    def getLongestStackIn(that:Array[Box]):Array[Box] = {
      val stackes:Array[Array[Box]] = for (stack <- this.getAllPossibleNextIn(that)) yield stack.getLongestStackIn(that filter (_.id != this.id));
      if (stackes.length>0) {
        Array[Box](this) ++ Box.biggestBoxesHeight(stackes);
      } else {
        Array[Box](this);
      }
    }

  }

object BoxApplication {
  def main(args: Array[String]) {
      Box.createFromList(scala.io.Source.stdin.getLines.toArray.tail);
      Box.dump;
      


  }

}


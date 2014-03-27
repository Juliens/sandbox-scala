import scala.io.Source;
import com.mongodb.casbah.Imports._

import java.io.FileInputStream;
import javax.imageio.ImageIO;
 
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.net.URL;

object abstractTypes {

  class QRCode {
    def decode(url:String):String = {
      new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new URL(url).openStream()))))).getText();
    }
  }

  class Fraction (n:Int, d:Int) {
      require (d!=0);
      def this(n:Int) = {
          this(n, 1);
      }
      val num = n/gcd(n,d);
      val denum = d/gcd(n,d);
      override def toString = num+"/"+denum
      def +(arg:Fraction):Fraction = {
          new Fraction(num*arg.denum + arg.num*denum, arg.denum*denum);
      }
      def +(arg:Int):Fraction = {
          this + new Fraction(arg)
      }
      def gcd(a:Int, b:Int):Int = {
          1
          /*
          if (b==0) a
          else gcd(b, a%b);
          */
      }
  }

  
  def main(args: Array[String]) {
      val file_list:Array[String] = (for (file <- new java.io.File("/home/juliens/dev/scala").listFiles
                            if (file.getName.endsWith(".scala"))
                        )
      yield file.getName).sortWith(_.toLowerCase < _.toLowerCase)


      for (file <- file_list) println(file);
      print(file_list);
      val mongoClient = MongoClient("localhost", 27017)
      val db = mongoClient("test")
      val coll = db("test");
      val a = MongoDBObject("hello" -> "world")
      val b = MongoDBObject("language" -> "scala")
      //coll.insert( a )
      //coll.insert( b )
      coll.find().foreach(println);

      val qr = new QRCode();
      println(qr.decode("http://www.alptis.org/qr_img.php?s=2&t=P&d=Essai%20de%20QR&bc=3"));


  }
}


### 用法：

java -jar signature-test-1.0-SNAPSHOT.jar [-multi int] [-seconds int] algorithmName
* multi：并发线程数，不知道默认为1，即不并发
* seconds：一轮的测试时间，单位为秒，默认为10秒
* algorithmName：算法名,可以指定多个，支持rsa、dsa、ecdsa

### 用例：

java -jar signature-test-1.0-SNAPSHOT.jar rsa<br>
启动一个线程，每轮用时10秒测试rsa算法性能

java -jar signature-test-1.0-SNAPSHOT.jar rsa dsa<br>
启动一个线程，每轮用时10秒测试rsa、dsa算法性能

java -jar signature-test-1.0-SNAPSHOT.jar -multi 2 -seconds 8 rsa<br>
启动2个线程，每轮用时8秒测试rsa算法性能
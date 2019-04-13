package tech.ccyyyy.test;
/**
 * @author zcy
 * @date 2019年4月12日 下午3:08:58
*/
public class ArrBlockingQueue {
	
	private Object data[]=new Object[3];
	private volatile int count=0;
	private volatile int rdx=0;//读指针
	private volatile int wdx=0;//写指针
	private volatile int  gap=0;//读指针和写指针之间的差
	
	
	//private final static Object full=new Object();
	//private final static Object empty=new Object();
	private final static Object lock=new Object();//
	
	
	public Object get() {
		Object object;
		synchronized (lock) {
			if(count==0) { 
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			object=data[rdx];
			System.out.println("拿出："+object+"，当前index："+rdx+",当前大小:"+count);
			if(++rdx==data.length) {
				rdx=0;
			}
			count--;
			lock.notify();
		}
		return object;
	}
	public void put(Object obj) {
		synchronized (lock) {
			if(count>=data.length) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				wdx=0;//从0开始写
			}

			data[wdx]=obj;
			System.out.println("放入:"+obj+"，当前index:"+wdx+",当前大小："+count);
			if(++wdx==data.length) {
				wdx=0;
			}
			count++;
			lock.notify();
		}
	}
	
	public static void main(String[] args) {
		
		ArrBlockingQueue queue=new ArrBlockingQueue();
		
		ReadThread readThread=new ReadThread(queue);
		WriteThread writeThread=new WriteThread(queue);
		
		Thread rThread=new Thread(readThread);
		Thread wThread=new Thread(writeThread);
		
		rThread.start();
		wThread.start();
		
	}

}

class ReadThread implements Runnable{
	
	private ArrBlockingQueue queue;

	public ReadThread(ArrBlockingQueue queue) {
		this.queue = queue;
	}

	public void run() {
		while(true) {
			System.out.println(queue.get());
		}
	}
	
}

class WriteThread implements Runnable{
	
	private ArrBlockingQueue queue;

	public WriteThread(ArrBlockingQueue queue) {
		this.queue = queue;
	}

	public void run() {
		for(int i=0;i<10;i++) {
			queue.put(i);
		}
	}
	
}

package com.meidusa.amoeba.mongodb.packet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;

import com.meidusa.amoeba.mongodb.io.MongodbPacketConstant;

/**
 * <h6><a name="MongoWireProtocol-OPREPLY"></a>OP_REPLY <a name="MongoWireProtocol-OPREPLY"></a></h6>
 * 
 * <p>The OP_REPLY message is sent by the database in response to an <a href="#MongoWireProtocol-OPQUERY">CONTRIB:OP_QUERY</a> or <a href="#MongoWireProtocol-OPGETMORE">CONTRIB:OP_GET_MORE</a> <br/>
 * 
 * message.  The format of an OP_REPLY message is:</p>
 * 
 * <div class="code panel" style="border-width: 1px;"><div class="codeContent panelContent">
 * <pre class="code-java">struct {
 *     MsgHeader header;         <span class="code-comment">// standard message header
 * </span>    int32     responseFlags;  <span class="code-comment">// bit vector - see details below
 * </span>    int64     cursorID;       <span class="code-comment">// cursor id <span class="code-keyword">if</span> client needs to <span class="code-keyword">do</span> get more's
 * 
 * </span>    int32     startingFrom;   <span class="code-comment">// where in the cursor <span class="code-keyword">this</span> reply is starting
 * </span>    int32     numberReturned; <span class="code-comment">// number of documents in the reply
 * </span>    document* documents;      <span class="code-comment">// documents
 * </span>}
 * </pre>
 * 
 * @author Struct
 *
 */
public class SimpleResponseMongodbPacket extends AbstractMongodbPacket {
	public int responseFlags;
	public long cursorID;
	
	public SimpleResponseMongodbPacket(){
		this.opCode = MongodbPacketConstant.OP_REPLY;
	}
	
	protected void init(MongodbPacketBuffer buffer) {
		super.init(buffer);
		responseFlags = buffer.readInt();
		cursorID = buffer.readLong();
		
	}

	@Override
	protected void write2Buffer(MongodbPacketBuffer buffer)
			throws UnsupportedEncodingException {
		super.write2Buffer(buffer);
		buffer.writeInt(responseFlags);
		buffer.writeLong(cursorID);
	}

}
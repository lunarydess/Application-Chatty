package zip.luzey.chatty.api.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.List;

public class CryptoCodec extends ByteToMessageCodec<ByteBuf> {
	private final Signature signature;
	private final PrivateKey privateKey;
	private final PublicKey publicKey;

	public CryptoCodec(KeyPair pair) throws NoSuchAlgorithmException {
		signature = Signature.getInstance("Ed25519");
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	@Override
	protected void encode(
		 ChannelHandlerContext ctx,
		 ByteBuf msg,
		 ByteBuf out
	) throws Exception {
		byte[] msgBytes = new byte[msg.readableBytes()];
		msg.getBytes(msg.readerIndex(), msgBytes);

		signature.initSign(privateKey);
		signature.update(msgBytes);
		byte[] signatureBytes = signature.sign();

		out.writeInt(msgBytes.length);
		out.writeBytes(msgBytes);
		out.writeBytes(signatureBytes);
	}

	@Override
	protected void decode(
		 ChannelHandlerContext ctx,
		 ByteBuf in,
		 List<Object> out
	) throws Exception {
		byte[] bytesMsg = new byte[in.readInt()];
		in.readBytes(bytesMsg);

		byte[] bytesSignature = new byte[in.readableBytes()];
		in.readBytes(bytesSignature);

		signature.initVerify(publicKey);
		signature.update(bytesMsg);

		if (signature.verify(bytesSignature))
			out.add(new String(bytesMsg, StandardCharsets.UTF_8));
		else
			throw new SecurityException("invalid signature");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
	}
}

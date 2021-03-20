package io.kimmking.rpcfx.common.exception;

/**
 * 统一封装的RpcfxException
 *
 * @author Liquid
 */
public class RpcfxException extends RuntimeException {
    private static final long serialVersionUID = -848964144333391207L;

    public RpcfxException() {
    }

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcfxException(Throwable cause) {
        super(cause);
    }
}

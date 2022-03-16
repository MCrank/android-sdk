package com.mobilecoin.lib.exceptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobilecoin.lib.UnsignedLong;

public class FogSyncException extends MobileCoinException {

    @Nullable
    private UnsignedLong viewBlockIndex;
    @Nullable
    private UnsignedLong consensusBlockIndex;

    public FogSyncException(@NonNull Throwable throwable) {
        super(throwable);
    }

    public FogSyncException(@Nullable String message) {
        super(message);
    }

    public FogSyncException(@Nullable String message, @Nullable Throwable exception) {
        super(message, exception);
    }

    public FogSyncException(@NonNull UnsignedLong viewBlockIndex, @NonNull UnsignedLong consensusBlockIndex) {
        super(String.format("Fog has not finished syncing with Consensus. Please try again later (Block index %s / %s).",
                viewBlockIndex, consensusBlockIndex));
        this.viewBlockIndex = viewBlockIndex;
        this.consensusBlockIndex = consensusBlockIndex;
    }

    @Nullable
    public UnsignedLong getViewBlockIndex() {
        return this.viewBlockIndex;
    }

    @Nullable
    public UnsignedLong getConsensusBlockIndex() {
        return this.consensusBlockIndex;
    }

}

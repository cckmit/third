                package com.beitie.designModel.model;

                public abstract class AbstractWorker {
                    public abstract void first();
                    public abstract void second();
                    public abstract void third();
                    public final void work(){
                        first();
                        second();
                        third();
                    }
                }
                    
package ioio.lib.impl;

import ioio.lib.api.exception.OutOfResourceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

class ModuleAllocator {
   private final Set allocatedModuleIds_;
   private final Set availableModuleIds_;
   private final String name_;

   public ModuleAllocator(int var1, String var2) {
      this(getList(var1), var2);
   }

   public ModuleAllocator(Collection var1, String var2) {
      this.availableModuleIds_ = new TreeSet(var1);
      this.allocatedModuleIds_ = new HashSet();
      this.name_ = var2;
   }

   public ModuleAllocator(int[] var1, String var2) {
      this(getList(var1), var2);
   }

   private static Collection getList(int var0) {
      ArrayList var1 = new ArrayList();

      for(int var2 = 0; var2 < var0; ++var2) {
         var1.add(Integer.valueOf(var2));
      }

      return var1;
   }

   private static Collection getList(int[] var0) {
      ArrayList var1 = new ArrayList(var0.length);
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.add(Integer.valueOf(var0[var3]));
      }

      return var1;
   }

   public Integer allocateModule() {
      synchronized(this){}

      Integer var2;
      try {
         if(this.availableModuleIds_.isEmpty()) {
            throw new OutOfResourceException("No more resources of the requested type: " + this.name_);
         }

         var2 = (Integer)this.availableModuleIds_.iterator().next();
         this.availableModuleIds_.remove(var2);
         this.allocatedModuleIds_.add(var2);
      } finally {
         ;
      }

      return var2;
   }

   public void releaseModule(int var1) {
      synchronized(this){}

      try {
         if(!this.allocatedModuleIds_.contains(Integer.valueOf(var1))) {
            throw new IllegalArgumentException("moduleId: " + var1 + "; not yet allocated");
         }

         this.availableModuleIds_.add(Integer.valueOf(var1));
         this.allocatedModuleIds_.remove(Integer.valueOf(var1));
      } finally {
         ;
      }

   }
}

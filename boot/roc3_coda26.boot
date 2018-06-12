# Boot file for CODA ROC 2.6
# PowerPC version

#loginUserAdd "abbottd","yzzbdbccd"

# Add route to outside world (from 29 subnet to 120 subnet)
#mRouteAdd("129.57.120.0","129.57.29.1",0xfffffc00,0,0)

# Load host table
#< /daqfs/home/abbottd/VXKERN/vxhosts.boot

# Setup environment to load coda_roc
putenv "MSQL_TCP_HOST=jlabdaq"
putenv "EXPID=cosmicraytelescope"
putenv "TCL_LIBRARY=/usr/local/coda/2.6/common/lib/tcl7.4"
putenv "ITCL_LIBRARY=/usr/local/coda/2.6/common/lib/itcl2.0"
putenv "DP_LIBRARY=/usr/local/coda/2.6/common/lib/dp"
putenv "SESSION=cosmictest"

# Load ppcTimer Library (set for 133MHz MV6100)
#ld < /daqfs/mizar/home/abbottd/vxWorks/ppcTimer/ppcTimer.o
#ppcTimeBaseFreqSet(133333332)


# Load Tempe DMA Library (for MV6100 CPUs)
#
#  To configure : usrVmeDmaConfig( addrType, dataType)
#     where addrType = 0 (A16)  1 (A24) or 2 (A32)
#           dataType = 0 (D16)  1 (D32)    2 (BLK32)
#                      3 (MBLT) 4 (2eVME)  5 (2eSST)
#
ld< /usr/local/coda/2.6/extensions/tempeDma/usrTempeDma.o
usrVmeDmaConfig(2,2)

# Load Universe DMA Library
#ld< /site/coda/2.5/extensions/universeDma/universeDma.o.mv5100
#initialize (no interrupts)
#sysVmeDmaInit(1) 
# Set for 64bit PCI transfers
#sysVmeDmaSet(4,1)
# A32 VME Slave
#sysVmeDmaSet(11,2)
# BLK32 (4) or MBLK(64) (5) VME transfers
#sysVmeDmaSet(12,4)

# Load SIS3320 QDC library
#ld< /mizar/home/abbottd/vxWorks/sis3300/sis3320Lib.o
#s3320Init(0x08000000,0,1,0)

# Load SIS3610 QDC library
#ld< /daqfs/mizar/home/abbottd/vxWorks/sis3610/sis3610Lib.o
#s3610Init(0xe800,0x1000,1)

# Load cMsg Stuff
cd "/usr/local/coda/2.6/VXWORKSPPC55"
ld< lib/libcmsgRegex.so
ld< lib/libcmsg.so

cd "/usr/local/coda/2.6/VXWORKSPPC55/bin"
ld < coda_roc_rc3.6

##Load F1 TDC Library 
#ld < /daqfs/mizar/home/abbottd/vxWorks/f1tdc/f1tdcLib.o
##ld < /daqfs/mizar/home/abbottd/vxWorks/f1tdc/f1tdcLib.o.HallC
## Init Single TDC for standalone - Normal Resolution
##f1Init(0xed0000,0x1000,1,0x02)
##f1EnableData(0,0)
##f1EnableSoftTrig(0)
##f1EnableBusError(0)
##f1Clear(0)
## Init 11 F1 TDCs with BDC and config file
#f1ConfigReadFile("/daqfs/mizar/home/abbottd/vxWorks/f1tdc/normal_118.dat")
#f1Init(0xed0000,0x1000,11,0xc)



# Spawn tasks
taskSpawn ("ROC",200,8,250000,coda_roc,"","-s","cosmictest","-objects","ROC3 ROC")

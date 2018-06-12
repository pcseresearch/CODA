# Boot file for CODA TS 2.6 
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

# Load Universe DMA Library
#ld< /mizar/home/abbottd/vxWorks/universeDma/universeDma.o
#initialize (no interrupts)
#sysVmeDmaInit(1) 
# Set for 64bit PCI transfers
#sysVmeDmaSet(4,1)
# A24 VME Slave
#sysVmeDmaSet(11,1)
# BLK32 (4) or MBLK(64) (5) VME transfers
#sysVmeDmaSet(12,4)


# Load cMsg Stuff
cd "/usr/local/coda/2.6/cMsg/vxworks-ppc/lib"
ld< libcmsgRegex.so
ld< libcmsg.so


# Get around vxWorks 5.5 Clock returning 0 bug
mytv = malloc(8)
clock_gettime(0,mytv)

# Load TS library for Live time calc support
ld < /usr/local/coda/2.6/VXWORKSPPC55/lib/tsUtil.o
tsInit(0,0)


cd "/usr/local/coda/2.6/VXWORKSPPC55/bin"
ld < coda_ts_rc3.6

# Spawn tasks
taskSpawn ("TSROC",200,8,250000,coda_roc,"","-s","cosmictest","-objects","TS1 TS")


# Program TS for Test Interrupts
# ROC Lock on all branches
#tsCsr2Set(0x03e0)
# Enable all 12 Trigger inputs
#tsEnableInput(0xfff,0)
# Setup Level2, 3 and FE Busy Timers
#tsTimerWrite(2,5)
#tsTimerWrite(3,5)
#tsTimerWrite(4,100)
# Enable FE Busy Timer
#tsCsr2Set(0x4)
# Setup Sync Event Interval and Enable Sync Events
#tsSync(200)
#tsCsr2Set(1)
# Connect Default ISR for TS interrupts and Enable Interrupts
#tsIntConnect(0,tsIntUser,0, 0)
#tsIntEnable(1)





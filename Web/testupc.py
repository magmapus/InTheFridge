import web
import json
from yummly import Yummly
from datetime import datetime, date, time

urls= (
	'/', 'index',
	'/checkin','checkin',
	'/status','status',
	'/frgData','frgData',
	'/frgupcData','frgupcData',
	'/slist','slist',
	'/rec','rec'
)


db = web.database(dbn='sqlite', db='test.sqlite')

class index:
	def GET(self):
		i=web.input()
		todos= db.select('upcs', where="id='"+i["upc"]+"'");
		try:
			upc=todos[0]
			return json.dumps(dict({'status':"G",'upc':i.upc}.items()+upc.items())) # combine the two. 
		except:
			return json.dumps({'status':"N", 'upc':i.upc})
	def POST(self):
		i=web.input()
		try:
			db.query("INSERT INTO upcs (id,itemname,itemtype,itempic,expiry) VALUES ('"+i["upc"]+"','"+i["name"]+"','"+i["type"]+"','"+i["pic"]+"','"+i["expiry"]+"')")
			return json.dumps({'status':"G",'upc':i.upc})
		except:
			return json.dumps({'status':"N",'upc':i.upc})
class checkin:
	def GET(self):
		i=web.input()
		todos= db.select('frg', where="upcid='"+i.upc+"'");
		try:
			db.query("UPDATE `frg` SET `quantity`=`quantity`-1,`name`='"+i["uname"]+"' WHERE `upcid`='"+i["upc"]+"' AND `quantity`>0")
			frg= db.select('frg', where="upcid='"+i.upc+"'");
			return json.dumps(dict({'status':"G",'upc':i.upc}.items()+frg[0].items()))
		except:
			return json.dumps({'status':"N", 'upc':i.upc})
	def POST(self):
		i=web.input()
		#try:
		quant=1
		try:
			quant=int(i["quant"])
		except:
			pass
		db.query("INSERT OR IGNORE INTO frg (upcid,name,expiry,quantity) VALUES ('"+i["upc"]+"','"+i["uname"]+"','"+i["expiry"]+"',0)")
		db.query("UPDATE `frg` SET `quantity`=`quantity`+"+str(quant)+",`name`='"+i["uname"]+"',`expiry`='"+i["expiry"]+"' WHERE `upcid`='"+i.upc+"'")
		db.query("DELETE from `slist2` WHERE `upc`='"+i["upc"]+"'")
		frg= db.select('frg', where="upcid='"+i.upc+"'");
		return json.dumps(dict({'status':"G",'upc':i.upc}.items()+frg[0].items()))

class status:
	def GET(self):
		i=web.input()
		frg= db.select('frg', where="upcid='"+i.upc+"'");
		#print frg[0].items()
		try:
			return json.dumps(dict({'status':"G",'upc':i.upc}.items()+frg[0].items()))
		except:
			return json.dumps({'status':"N", 'upc':i.upc})

class  frgData:
	def GET(self):
		frg= db.select('frg', where="quantity>0")
		ar=[]
		for a in frg:
			it=dict(a.items())
			it["status"]="G";
			it["upc"]=it["upcid"]
			ar.append(it)
		print ar
		return json.dumps(ar)

class slist:
	def GET(self):
		uc=db.select('slist2')
		ar=[]
		for i in uc:
			it=dict(i.items())
			uc2=db.select('upcs', where="id='"+it["upc"]+"'")
			it=dict(uc2[0].items())
			it["status"]="G"
			it["upc"]=it["id"]
			ar.append(it)
		return json.dumps(ar)
	def POST(self):
		i=web.input()
		db.query("INSERT INTO `slist2` (upc) VALUES (\'"+i["upc"]+"\')")

class  frgupcData:
	def GET(self):
		frg= db.select('frg', where="quantity>0")
		ar=[]
		for a in frg:
			d=dict(a.items())
			upc=db.select('upcs', where="id='"+d["upcid"]+"'")
			it=dict(upc[0].items())
			it["status"]="G"
			it["upc"]=it["id"]
			ar.append(it)
		return json.dumps(ar)

class rec:
	def GET(self):
		frg= db.select('frg', where="quantity>0")
		ar=[]
		for a in frg:
			d=dict(a.items())
			upc=db.select('upcs', where="id='"+d["upcid"]+"'")
			it=dict(upc[0].items())
			ar.append(it["itemtype"])
		y=Yummly()
		y.setup('d579507d','736c339e12b7a287c72c2de82313657e')
		dtme=datetime.now()
		z=dtme.hour
		course="course^course-Breakfast and Brunch"
		if(z > 10):
			course="course^course-Lunch and Snacks"
		elif (z>16):
			course="course^course-Main Dishes"
		print "using course:"+course
		return json.dumps(y.getAll(ar,course))


if __name__ == "__main__": 
    app = web.application(urls, globals())
    app.run()

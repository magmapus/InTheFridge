import web
import json

urls= (
	'/', 'index'
)


db = web.database(dbn='sqlite', db='test.sqlite')

class index:
	def GET(self):
		i=web.input()
		todos= db.select('upcs', where="id='"+i.upc+"'");
		try:
			upc=todos[0]
			return json.dumps(dict({'status':"G"}.items()+upc.items())) # combine the two. 
		except:
			return json.dumps({'status':"N", 'upc':i.upc})
	def POST(self):
		i=web.input()
		try:
			db.query("INSERT INTO upcs (id,itemname,itemtype,itempic,expiry) VALUES ('"+i["upc"]+"','"+i["name"]+"','"+i["type"]+"','"+i["pic"]+"','"+i["expiry"]+"')")
			return json.dumps({'status':"G"})
		except:
			return json.dumps({'status':"N"})
if __name__ == "__main__": 
    app = web.application(urls, globals())
    app.run()
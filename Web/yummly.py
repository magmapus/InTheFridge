import requests
import urllib
import json
class Yummly:
	s=requests.Session()
	base="http://api.yummly.com/v1/api/recipes"
	def setup(self,appid,appkey):
		self.s.headers.update({'X-Yummly-App-ID':appid,'X-Yummly-App-Key':appkey})
	def qsearch(self,ing,course):
		pms=urllib.quote("allowedCourse[]")+"="+urllib.quote(course)
		pms=pms+"&"+urllib.quote("allowedIngredient[]")+"="+urllib.quote(ing)
		q=self.s.get(self.base+"?"+pms)
		return json.loads(q.text)["matches"]
	def getRecipie(self,rid):
		pms=urllib.quote(rid)
		q=self.s.get("http://api.yummly.com/v1/api/recipe/"+pms)
		rec=json.loads(q.text)
		return {'url':rec["attribution"]["url"], 'image':rec["images"][0]["hostedLargeUrl"],'name':rec["name"],'time':rec["totalTime"],'rating':rec["rating"]}
	def getAll(self,ing,course):
		total=[]
		for item in ing:
			t=self.qsearch(item,course)
			for k in t:
				k["rank"]=1
				k["needmore"]=len(k["ingredients"])
				for i in k["ingredients"]:
					for x in ing:
						if i.find(x) != -1:
							k["rank"]=k["rank"]+1
							k["needmore"]=k["needmore"]-1
				k["rank"]=k["rank"]-k["needmore"]
				total.append(k)
		final=[]
		ids=[]
		for it in total:
			if it["id"] not in ids:
				ids.append(it["id"])
				final.append(it)

		newlist = sorted(final, key=lambda k: k['rank'])
		newlist.reverse()
		last=[]
		for r in  newlist[:10]:
			last.append(self.getRecipie(r["id"]))
		return last
	def getCourses(self):
		q=self.s.get("http://api.yummly.com/v1/api/metadata/course")
		print q.text
if __name__ == "__main__":
	y=Yummly()
	y.setup('d579507d','736c339e12b7a287c72c2de82313657e')
	ing=["fruit","milk"]
	print y.getAll(ing,"course^course-Beverages")
	y.getCourses()
	#todo